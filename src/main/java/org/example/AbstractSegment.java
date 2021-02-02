package org.example;

import org.example.Model.SegmentType;

public abstract class AbstractSegment implements Segment
{
   protected final SegmentType segmentType;
   protected AbstractSegment(SegmentType segmentType)
   {
      this.segmentType = segmentType;
   }

   public SegmentType getSegmentType()
   {
      return this.segmentType;
   }

   public String toString()
   {
      return string(new StringBuilder()).toString();
   }
}