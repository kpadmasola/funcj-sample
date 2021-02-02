package org.example;

import org.typemeta.funcj.data.IList;

public class Model
{
   public static Path path(Segment segment)
   {
      return new Path(IList.of(), segment);
   }

   public static Path path(IList<Segment> segments, Segment segment)
   {
      return new Path(segments, segment);
   }

   public enum SegmentType
   {
      NORMAL,
      QUOTED,
      WILDCARD,
      WILDCARD_SUFFIX
   }

   public static class Path
   {
      private final IList<Segment> segments;
      private final Segment lastSegment;

      public Path(IList<Segment> segments, Segment lastSegment)
      {
         this.segments = segments;
         this.lastSegment = lastSegment;
      }

      public Segment getLastSegment()
      {
         return lastSegment;
      }

      public String toString()
      {
         StringBuilder sb = new StringBuilder();
         for (Segment s : segments)
         {
            sb = s.string(sb).append('.');
         }

         return lastSegment.string(sb).toString();
      }
   }

   public static class NormalSegment extends AbstractSegment
   {
      private final String value;

      public NormalSegment(String value)
      {
         super(SegmentType.NORMAL);
         this.value = value;
      }

      @Override
      public StringBuilder string(StringBuilder sb)
      {
         return sb.append(value);
      }
   }

   public static Segment normalSegment(String text)
   {
      return new NormalSegment(text);
   }

   public static class QuotedSegment extends AbstractSegment
   {
      private final String value;

      public QuotedSegment(String value)
      {
         super(SegmentType.QUOTED);
         this.value = value;
      }

      @Override
      public StringBuilder string(StringBuilder sb)
      {
         return sb.append(value);
      }
   }

   public static Segment quotedSegment(String value)
   {
      return new QuotedSegment(value);
   }

   public static class WildcardSegment extends AbstractSegment
   {
      protected WildcardSegment()
      {
         super(SegmentType.WILDCARD);
      }

      @Override
      public StringBuilder string(StringBuilder sb)
      {
         return sb.append("*");
      }
   }

   public static class WildcardSuffixSegment extends AbstractSegment
   {
      private final Segment value;

      public WildcardSuffixSegment(Segment value)
      {
         super(SegmentType.WILDCARD_SUFFIX);
         this.value = value;
      }

      @Override
      public StringBuilder string(StringBuilder sb)
      {
         return sb.append(value).append("*");
      }
   }

}
