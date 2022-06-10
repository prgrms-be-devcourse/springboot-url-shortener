package org.prgrms.kdt.url.generator;

public interface IdGenerator {

  char[] DEFAULT_CHARACTER_SET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

  String generate();
}