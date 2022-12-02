package com.vmwork.pharmaceuticalcatalog;

import com.vmwork.pharmaceuticalcatalog.ui.Menu;

public class Main {

  public static void main(String[] args) {
    Menu.baseMenu();
  }

}

//package com.vmwork.pharmaceuticalcatalog;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.nio.charset.Charset;
//import java.util.Locale;
//
//import static java.lang.System.out;
//
///**
// * Demonstrate default Charset-related details.
// */
//public class Main {
//
//  /**
//   * Supplies the default encoding without using Charset.defaultCharset() and without accessing
//   * System.getProperty("file.encoding").
//   *
//   * @return Default encoding (default charset).
//   */
//  public static String getEncoding() {
//    final byte[] bytes = {'D'};
//    final InputStream inputStream = new ByteArrayInputStream(bytes);
//    final InputStreamReader reader = new InputStreamReader(inputStream);
//    final String encoding = reader.getEncoding();
//    return encoding;
//  }
//
//  public static void main(final String[] arguments) {
//    out.println("Default Locale:   " + Locale.getDefault());
//    out.println("Default Charset:  " + Charset.defaultCharset());
//    out.println("file.encoding;    " + System.getProperty("file.encoding"));
//    out.println("sun.jnu.encoding: " + System.getProperty("sun.jnu.encoding"));
//    out.println("Default Encoding: " + getEncoding());
//  }
//}
