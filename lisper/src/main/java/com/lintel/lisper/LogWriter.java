/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.7
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.lintel.lisper;

public class LogWriter {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected LogWriter(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(LogWriter obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        pjsua2JNI.delete_LogWriter(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  protected void swigDirectorDisconnect() {
    swigCMemOwn = false;
    delete();
  }

  public void swigReleaseOwnership() {
    swigCMemOwn = false;
    pjsua2JNI.LogWriter_change_ownership(this, swigCPtr, false);
  }

  public void swigTakeOwnership() {
    swigCMemOwn = true;
    pjsua2JNI.LogWriter_change_ownership(this, swigCPtr, true);
  }

  public void write(LogEntry entry) {
    pjsua2JNI.LogWriter_write(swigCPtr, this, LogEntry.getCPtr(entry), entry);
  }

  public LogWriter() {
    this(pjsua2JNI.new_LogWriter(), true);
    pjsua2JNI.LogWriter_director_connect(this, swigCPtr, swigCMemOwn, true);
  }

}
