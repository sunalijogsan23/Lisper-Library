/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.7
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.lintel.lisper;

public class MediaSize {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected MediaSize(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(MediaSize obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        pjsua2JNI.delete_MediaSize(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setW(long value) {
    pjsua2JNI.MediaSize_w_set(swigCPtr, this, value);
  }

  public long getW() {
    return pjsua2JNI.MediaSize_w_get(swigCPtr, this);
  }

  public void setH(long value) {
    pjsua2JNI.MediaSize_h_set(swigCPtr, this, value);
  }

  public long getH() {
    return pjsua2JNI.MediaSize_h_get(swigCPtr, this);
  }

  public MediaSize() {
    this(pjsua2JNI.new_MediaSize(), true);
  }

}
