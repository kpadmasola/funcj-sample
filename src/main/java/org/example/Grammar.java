package org.example;

import static org.typemeta.funcj.parser.Combinators.choice;
import static org.typemeta.funcj.parser.Combinators.satisfy;
import static org.typemeta.funcj.parser.Combinators.value;
import static org.typemeta.funcj.parser.Parser.pure;
import static org.typemeta.funcj.parser.Text.alphaNum;
import static org.typemeta.funcj.parser.Text.chr;

import org.example.Model.Path;
import org.example.Model.WildcardSegment;
import org.example.Model.WildcardSuffixSegment;
import org.typemeta.funcj.data.Chr;
import org.typemeta.funcj.parser.Input;
import org.typemeta.funcj.parser.Parser;
import org.typemeta.funcj.parser.Result;
import org.typemeta.funcj.parser.Text;

public abstract class Grammar
{
   private static final Parser<Chr, Chr> period = chr('.');
   private static final Parser<Chr, Chr> asterisk = chr('*');
   private static final Parser<Chr, Chr> underscore = chr('_');

   private static final Parser<Chr, Byte> digit = Text.digit.map(c -> (byte) Chr.digit(c.charValue(), 10));
   private static final Parser<Chr, Byte> hexA = chr('a').or(chr('A')).map(u -> (byte) 10);
   private static final Parser<Chr, Byte> hexB = chr('b').or(chr('B')).map(u -> (byte) 11);
   private static final Parser<Chr, Byte> hexC = chr('c').or(chr('C')).map(u -> (byte) 12);
   private static final Parser<Chr, Byte> hexD = chr('d').or(chr('D')).map(u -> (byte) 13);
   private static final Parser<Chr, Byte> hexE = chr('e').or(chr('E')).map(u -> (byte) 14);
   private static final Parser<Chr, Byte> hexF = chr('f').or(chr('F')).map(u -> (byte) 15);

   private static final Parser<Chr, Byte> hexDigit =
      choice(
         digit,
         hexA,
         hexB,
         hexC,
         hexD,
         hexE,
         hexF
      );

   private static final Parser<Chr, Chr> uni =
      hexDigit.and(hexDigit).and(hexDigit).and(hexDigit)
         .map((d0, d1, d2, d3) ->
            (d0.intValue() << 12) |
               (d1.intValue() << 8) |
               (d2.intValue() << 4) |
               d3.intValue())
         .map(Chr::valueOf);

   private static final Parser<Chr, Chr> uChr = chr('u');
   private static final Parser<Chr, Chr> bsChr = chr('\\');
   private static final Parser<Chr, Chr> dqChr = chr('"');

   private static final Parser<Chr, Chr> esc =
      choice(
         dqChr,
         bsChr,
         chr('/'),
         value(Chr.valueOf('b'), Chr.valueOf('\b')),
         value(Chr.valueOf('f'), Chr.valueOf('\f')),
         value(Chr.valueOf('n'), Chr.valueOf('\n')),
         value(Chr.valueOf('r'), Chr.valueOf('\r')),
         value(Chr.valueOf('t'), Chr.valueOf('\t')),
         uChr.andR(uni)
      );

   private static final Parser<Chr, Chr> stringChar =
      (bsChr.andR(esc)).or(
         satisfy("schar", c ->
            !c.equals('"') &&
               !c.equals('\\') &&
               !c.equals('\t') &&
               !c.equals('\r') &&
               !c.equals('\n'))
      );

   private static final Parser<Chr, Segment> quotedSegment =
      stringChar
         .many()
         .map(Chr::listToString)
         .between(dqChr, dqChr)
         .map(Model::quotedSegment);

   private static final Parser<Chr, Segment> normalSegment =
      alphaNum
         .or(underscore)
         .many1()
         .map(Chr::listToString)
         .map(Model::normalSegment);

   private static final Parser<Chr, Segment> wildcardSegment =
      asterisk.andR(pure(new WildcardSegment()));

   private static final Parser<Chr, Segment> wildcardSuffixSegment =
      quotedSegment
         .andL(asterisk)
         .map(WildcardSuffixSegment::new);

   private static final Parser<Chr, Path> singleSegmentPath =
      choice(
         quotedSegment,
         wildcardSuffixSegment,
         wildcardSegment,
         normalSegment
      ).map(Model::path);

   private static final Parser<Chr, Path> parser =
      choice(normalSegment, quotedSegment)
         .andL(period).many()
         .and(choice(
            quotedSegment,
            wildcardSuffixSegment,
            wildcardSegment,
            normalSegment
         ))
         .map(segments -> segment -> Model.path(segments, segment));

   public static Result<Chr, Path> parse(String s)
   {
      return parser.parse(Input.of(s));
   }
}
