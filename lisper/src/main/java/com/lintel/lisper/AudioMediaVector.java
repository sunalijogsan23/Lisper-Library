/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.7
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.lintel.lisper;

public class AudioMediaVector {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected AudioMediaVector(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(AudioMediaVector obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        pjsua2JNI.delete_AudioMediaVector(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public AudioMediaVector() {
    this(pjsua2JNI.new_AudioMediaVector__SWIG_0(), true);
  }

  public AudioMediaVector(long n) {
    this(pjsua2JNI.new_AudioMediaVector__SWIG_1(n), true);
  }

  public long size() {
    return pjsua2JNI.AudioMediaVector_size(swigCPtr, this);
  }

  public long capacity() {
    return pjsua2JNI.AudioMediaVector_capacity(swigCPtr, this);
  }

  public void reserve(long n) {
    pjsua2JNI.AudioMediaVector_reserve(swigCPtr, this, n);
  }

  public boolean isEmpty() {
    return pjsua2JNI.AudioMediaVector_isEmpty(swigCPtr, this);
  }

  public void clear() {
    pjsua2JNI.AudioMediaVector_clear(swigCPtr, this);
  }

  public void add(AudioMedia x) {
    pjsua2JNI.AudioMediaVector_add(swigCPtr, this, AudioMedia.getCPtr(x), x);
  }

  public AudioMedia get(int i) {
    long cPtr = pjsua2JNI.AudioMediaVector_get(swigCPtr, this, i);
    return (cPtr == 0) ? null : new AudioMedia(cPtr, false);
  }

  public void set(int i, AudioMedia val) {
    pjsua2JNI.AudioMediaVector_set(swigCPtr, this, i, AudioMedia.getCPtr(val), val);
  }

}
