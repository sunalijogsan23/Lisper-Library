/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.7
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.lintel.lisper;

public class AccountMwiConfig extends PersistentObject {
  private transient long swigCPtr;

  protected AccountMwiConfig(long cPtr, boolean cMemoryOwn) {
    super(pjsua2JNI.AccountMwiConfig_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(AccountMwiConfig obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        pjsua2JNI.delete_AccountMwiConfig(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public void setEnabled(boolean value) {
    pjsua2JNI.AccountMwiConfig_enabled_set(swigCPtr, this, value);
  }

  public boolean getEnabled() {
    return pjsua2JNI.AccountMwiConfig_enabled_get(swigCPtr, this);
  }

  public void setExpirationSec(long value) {
    pjsua2JNI.AccountMwiConfig_expirationSec_set(swigCPtr, this, value);
  }

  public long getExpirationSec() {
    return pjsua2JNI.AccountMwiConfig_expirationSec_get(swigCPtr, this);
  }

  public void readObject(ContainerNode node) throws java.lang.Exception {
    pjsua2JNI.AccountMwiConfig_readObject(swigCPtr, this, ContainerNode.getCPtr(node), node);
  }

  public void writeObject(ContainerNode node) throws java.lang.Exception {
    pjsua2JNI.AccountMwiConfig_writeObject(swigCPtr, this, ContainerNode.getCPtr(node), node);
  }

  public AccountMwiConfig() {
    this(pjsua2JNI.new_AccountMwiConfig(), true);
  }

}
