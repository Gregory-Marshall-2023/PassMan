package com.gtmarshall.FileManagement;

import java.awt.*;
import java.security.SecureRandom;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PasswordGenerator {
  private PasswordGenerator() {}

  public enum PasswordType {
    AlphaNumeric,
    AlphaNumeric_No_caps, // only known use for this is RuneScape
    AlphaNumeric_With_Special
  }

  private static final String lowercase = "abcdefghijklmnopqrstuvwxyz";
  private static final String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String numbers = "0123459789";
  private static final String special = " !#$%&()*+,-./:;<=>?@[\\]^_`{|}~";
  private static SecureRandom random;

  public static String generate(int size, PasswordType type) {
    List<String> charsets = new ArrayList<>();
    switch (type) {
      case AlphaNumeric_With_Special:
        charsets.add(special);
      case AlphaNumeric:
        charsets.add(uppercase);
      case AlphaNumeric_No_caps:
        charsets.add(lowercase);
        charsets.add(numbers);
    }
    Collections.shuffle(charsets);
    return generate(size, charsets);
  }

  private static String generate(int size, List<String> charsets) {
    if (random == null) random = new SecureRandom();
    int[] sizes = new int[charsets.size()];
    int evenSize =
        (int)
            Math.ceil(
                ((float) size / 3f)
                    / charsets.size()); // ammt given to every charset (1/3rd rounded up to nearest
    // charset count)
    int rem = size - evenSize * charsets.size();
    String ret = "";
    for (int i = 0; i < charsets.size(); i++) {
      ret += stringFromSet(charsets.get(i), evenSize);
    }
    for (int i; rem > 0; rem--) {
      ret += stringFromSet(charsets.get(random.nextInt(charsets.size())), 1);
    }
    return Shuffle(Shuffle(ret));
  }

  private static String stringFromSet(String charset, int size) {
    if (random == null) random = new SecureRandom();
    String ret = "";
    random.setSeed(System.nanoTime() & System.currentTimeMillis());
    for (int i = 0; i < size; i++) ret += charset.charAt(random.nextInt(charset.length()));

    return ret;
  }

  public static String Shuffle(String text) {
    List<Character> characters = text.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
    StringBuilder result = new StringBuilder();
    IntStream.range(0, text.length())
        .forEach(
            (index) -> {
              int randomPosition = new Random().nextInt(characters.size());
              result.append(characters.get(randomPosition));
              characters.remove(randomPosition);
            });
    return result.toString();
  }
}
