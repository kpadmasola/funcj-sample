package org.example;

import org.example.Model.Path;
import org.typemeta.funcj.data.Chr;
import org.typemeta.funcj.parser.Result;

/**
 * Hello world!
 */
public class App
{
   public static void main(String[] args)
   {
      System.out.println("Hello World!");

      Result<Chr, Path> result;
      // a
      result = Grammar.parse("a");
      System.out.println("result: " + result);
      result.getOrThrow();

      // *
      result = Grammar.parse("*");
      System.out.println("result: " + result);
      result.getOrThrow();

      // "a"
      result = Grammar.parse("\"a\"");
      System.out.println("result: " + result);
      result.getOrThrow();

      // "a.b"
      result = Grammar.parse("\"a.b\"");
      System.out.println("result: " + result);
      result.getOrThrow();

      // "a.b*"
      result = Grammar.parse("\"a.b*\"");
      System.out.println("result: " + result);
      result.getOrThrow();

      // "a.*b"
      result = Grammar.parse("\"a.*b\"");
      System.out.println("result: " + result);
      result.getOrThrow();

      // "*"
      result = Grammar.parse("\"*\"");
      System.out.println("result: " + result);
      result.getOrThrow();

      // !!!!! ** multiple segments *****
      // a.b
      result = Grammar.parse("a.b");
      System.out.println("result: " + result);
      result.getOrThrow();

      // a.*
      result = Grammar.parse("a.\"*\"");
      System.out.println("result: " + result);
      result.getOrThrow();

      // "a".b
      result = Grammar.parse("\"a\".b");
      System.out.println("result: " + result);
      result.getOrThrow();

      // "a"."b"
      result = Grammar.parse("\"a\".\"b\"");
      System.out.println("result: " + result);
      result.getOrThrow();

      // a."b"
      result = Grammar.parse("a.\"b\"");
      System.out.println("result: " + result);
      result.getOrThrow();

      // "a.b".c
      result = Grammar.parse("\"a.b\".c");
      System.out.println("result: " + result);
      result.getOrThrow();

      // "a.b"."c"
      result = Grammar.parse("\"a.b\".\"c\"");
      System.out.println("result: " + result);
      result.getOrThrow();

      // a."b.c"
      result = Grammar.parse("a.\"b.c\"");
      System.out.println("result: " + result);
      result.getOrThrow();

      // a.b.c
      result = Grammar.parse("a.b.c");
      System.out.println("result: " + result);
      result.getOrThrow();

      // "a.b*".c
      result = Grammar.parse("\"a.b*\".c");
      System.out.println("result: " + result);
      result.getOrThrow();

      // "a.*b".c
      result = Grammar.parse("\"a.*b\".c");
      System.out.println("result: " + result);
      result.getOrThrow();

      // a."*"
      result = Grammar.parse("a.\"*\"");
      System.out.println("result: " + result);
      result.getOrThrow();

      // "a"."*"
      result = Grammar.parse("\"a\".\"*\"");
      System.out.println("result: " + result);
      result.getOrThrow();

      // "a"*
      result = Grammar.parse("\"a\"*");
      System.out.println("result: " + result);
      result.getOrThrow();

      // "a.b"*
      result = Grammar.parse("\"a.b\"*");
      System.out.println("result: " + result);
      result.getOrThrow();

      // a.b* -- should fail
      result = Grammar.parse("a.b*");
      System.out.println("result: " + result);
      result.getOrThrow();

   }
}
