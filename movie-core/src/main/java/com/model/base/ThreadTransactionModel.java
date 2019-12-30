package com.model.base;

import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.TransactionalCallable;
import org.jooq.TransactionalRunnable;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Stack;

@Slf4j
@Component
public class ThreadTransactionModel {
  private final ThreadLocal<Stack<DSLContext>> threadLocalContexts = ThreadLocal.withInitial(Stack::new);

  private static ThreadLocal<ThreadTransactionContext> transactionContext = new ThreadLocal<>();

  @Autowired
  protected DSLContext dslContext;

  public void transaction(TransactionalRunnable transactionalRunnable) {
    if (transactionContext.get() == null) {
      transactionContext.set(new ThreadTransactionContext());
    }

    getDslContext().transaction(configuration -> {
      DSLContext dsl = DSL.using(configuration);
      threadLocalContexts.get().push(dsl);
      try {
        transactionalRunnable.run(configuration);
      } finally {
        threadLocalContexts.get().pop();
        try {
          if (threadLocalContexts.get().empty()) {
            transactionContext.remove();
          }
        } catch (Throwable e) {
          log.error("事务执行异常", e);
        }
      }
    });
  }

  public <T> T transactionResult(TransactionalCallable<T> transactionalCallable) {
    if (transactionContext.get() == null) {
      transactionContext.set(new ThreadTransactionContext());
    }

    return getDslContext().transactionResult(configuration -> {
      DSLContext dsl = DSL.using(configuration);
      threadLocalContexts.get().push(dsl);
      try {
        return transactionalCallable.run(configuration);
      } finally {
        threadLocalContexts.get().pop();
        try {
          if (threadLocalContexts.get().empty()) {
            transactionContext.remove();
          }
        } catch (Throwable e) {
          log.error("事务执行异常", e);
        }
      }
    });
  }

  private DSLContext getThreadLocalDslContext() {
    Stack<DSLContext> dslContexts = threadLocalContexts.get();
    if (dslContexts.empty()) {
      return null;
    }
    return dslContexts.peek();
  }

  public DSLContext getDslContext() {
    DSLContext threadLocalDslContext = getThreadLocalDslContext();
    if (threadLocalDslContext != null) {
      return threadLocalDslContext;
    }
    return dslContext;
  }

  public boolean isInTransaction() {
    return !threadLocalContexts.get().empty();
  }

  public static ThreadTransactionContext getTransactionContext() {
    return transactionContext.get();
  }
}

