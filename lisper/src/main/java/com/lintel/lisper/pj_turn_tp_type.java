/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.7
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.lintel.lisper;

public final class pj_turn_tp_type {
  public final static pj_turn_tp_type PJ_TURN_TP_UDP = new pj_turn_tp_type("PJ_TURN_TP_UDP", pjsua2JNI.PJ_TURN_TP_UDP_get());
  public final static pj_turn_tp_type PJ_TURN_TP_TCP = new pj_turn_tp_type("PJ_TURN_TP_TCP", pjsua2JNI.PJ_TURN_TP_TCP_get());
  public final static pj_turn_tp_type PJ_TURN_TP_TLS = new pj_turn_tp_type("PJ_TURN_TP_TLS", pjsua2JNI.PJ_TURN_TP_TLS_get());

  public final int swigValue() {
    return swigValue;
  }

  public String toString() {
    return swigName;
  }

  public static pj_turn_tp_type swigToEnum(int swigValue) {
    if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
      return swigValues[swigValue];
    for (int i = 0; i < swigValues.length; i++)
      if (swigValues[i].swigValue == swigValue)
        return swigValues[i];
    throw new IllegalArgumentException("No enum " + pj_turn_tp_type.class + " with value " + swigValue);
  }

  private pj_turn_tp_type(String swigName) {
    this.swigName = swigName;
    this.swigValue = swigNext++;
  }

  private pj_turn_tp_type(String swigName, int swigValue) {
    this.swigName = swigName;
    this.swigValue = swigValue;
    swigNext = swigValue+1;
  }

  private pj_turn_tp_type(String swigName, pj_turn_tp_type swigEnum) {
    this.swigName = swigName;
    this.swigValue = swigEnum.swigValue;
    swigNext = this.swigValue+1;
  }

  private static pj_turn_tp_type[] swigValues = { PJ_TURN_TP_UDP, PJ_TURN_TP_TCP, PJ_TURN_TP_TLS };
  private static int swigNext = 0;
  private final int swigValue;
  private final String swigName;
}

