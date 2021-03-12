/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.7
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.lintel.lisper;

public final class pjsip_inv_state {
  public final static pjsip_inv_state PJSIP_INV_STATE_NULL = new pjsip_inv_state("PJSIP_INV_STATE_NULL");
  public final static pjsip_inv_state PJSIP_INV_STATE_CALLING = new pjsip_inv_state("PJSIP_INV_STATE_CALLING");
  public final static pjsip_inv_state PJSIP_INV_STATE_INCOMING = new pjsip_inv_state("PJSIP_INV_STATE_INCOMING");
  public final static pjsip_inv_state PJSIP_INV_STATE_EARLY = new pjsip_inv_state("PJSIP_INV_STATE_EARLY");
  public final static pjsip_inv_state PJSIP_INV_STATE_CONNECTING = new pjsip_inv_state("PJSIP_INV_STATE_CONNECTING");
  public final static pjsip_inv_state PJSIP_INV_STATE_CONFIRMED = new pjsip_inv_state("PJSIP_INV_STATE_CONFIRMED");
  public final static pjsip_inv_state PJSIP_INV_STATE_DISCONNECTED = new pjsip_inv_state("PJSIP_INV_STATE_DISCONNECTED");

  public final int swigValue() {
    return swigValue;
  }

  public String toString() {
    return swigName;
  }

  public static pjsip_inv_state swigToEnum(int swigValue) {
    if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
      return swigValues[swigValue];
    for (int i = 0; i < swigValues.length; i++)
      if (swigValues[i].swigValue == swigValue)
        return swigValues[i];
    throw new IllegalArgumentException("No enum " + pjsip_inv_state.class + " with value " + swigValue);
  }

  private pjsip_inv_state(String swigName) {
    this.swigName = swigName;
    this.swigValue = swigNext++;
  }

  private pjsip_inv_state(String swigName, int swigValue) {
    this.swigName = swigName;
    this.swigValue = swigValue;
    swigNext = swigValue+1;
  }

  private pjsip_inv_state(String swigName, pjsip_inv_state swigEnum) {
    this.swigName = swigName;
    this.swigValue = swigEnum.swigValue;
    swigNext = this.swigValue+1;
  }

  private static pjsip_inv_state[] swigValues = { PJSIP_INV_STATE_NULL, PJSIP_INV_STATE_CALLING, PJSIP_INV_STATE_INCOMING, PJSIP_INV_STATE_EARLY, PJSIP_INV_STATE_CONNECTING, PJSIP_INV_STATE_CONFIRMED, PJSIP_INV_STATE_DISCONNECTED };
  private static int swigNext = 0;
  private final int swigValue;
  private final String swigName;
}

