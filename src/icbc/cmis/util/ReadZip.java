package icbc.cmis.util;

import java.io.File;
import java.util.zip.ZipFile;
import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ReadZip {

  public ReadZip() {
  }

  public void readFile(String fname) {
    try {
      ZipFile zipFile = new ZipFile(fname);
      Enumeration entries = zipFile.entries();
      while (entries.hasMoreElements()) {
        ZipEntry entry = (ZipEntry)entries.nextElement();
        if(!entry.isDirectory()) {
          String name = entry.getName();
          int k = name.lastIndexOf("/");
          if(k > 0) name = name.substring(k + 1);
          System.out.println(name);
        }
      }
    }
    catch (IOException ex) {
      /** @todo log error */
      System.out.println("read zipfile [" + fname + "] error!" + ex.getMessage());
    }
  }

  public static void main(String[] args) {
    if(args.length < 1) {
      System.out.println("Useage: java ReadZip xxx.zip");
    }
    ReadZip readZip1 = new ReadZip();
    readZip1.readFile(args[0]);
  }
}