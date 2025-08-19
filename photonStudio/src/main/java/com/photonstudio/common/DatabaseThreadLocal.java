package com.photonstudio.common;

/**
 * 数据库名存放ThreadLocal
 *
 * @author bingo
 */
public class DatabaseThreadLocal {
  private static final ThreadLocal<String> databaseThreadLocal = new ThreadLocal<>();

  public static void set(String database) {
    databaseThreadLocal.set(database);
  }

  public static String get() {
    return databaseThreadLocal.get();
  }

  public static void remove() {
    databaseThreadLocal.remove();
  }
}
