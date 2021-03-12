/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.7
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.lintel.lisper;

public class OnCallMediaEventParam {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected OnCallMediaEventParam(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(OnCallMediaEventParam obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        pjsua2JNI.delete_OnCallMediaEventParam(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setMedIdx(long value) {
    pjsua2JNI.OnCallMediaEventParam_medIdx_set(swigCPtr, this, value);
  }

  public long getMedIdx() {
    return pjsua2JNI.OnCallMediaEventParam_medIdx_get(swigCPtr, this);
  }

  public void setEv(MediaEvent value) {
    pjsua2JNI.OnCallMediaEventParam_ev_set(swigCPtr, this, MediaEvent.getCPtr(value), value);
  }

  public MediaEvent getEv() {
    long cPtr = pjsua2JNI.OnCallMediaEventParam_ev_get(swigCPtr, this);
    return (cPtr == 0) ? null : new MediaEvent(cPtr, false);
  }

  public OnCallMediaEventParam() {
    this(pjsua2JNI.new_OnCallMediaEventParam(), true);
  }

}
