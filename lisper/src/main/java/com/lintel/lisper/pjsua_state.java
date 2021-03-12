/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.7
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.lintel.lisper;

public final class pjsua_state {
  public final static pjsua_state PJSUA_STATE_NULL = new pjsua_state("PJSUA_STATE_NULL");
  public final static pjsua_state PJSUA_STATE_CREATED = new pjsua_state("PJSUA_STATE_CREATED");
  public final static pjsua_state PJSUA_STATE_INIT = new pjsua_state("PJSUA_STATE_INIT");
  public final static pjsua_state PJSUA_STATE_STARTING = new pjsua_state("PJSUA_STATE_STARTING");
  public final static pjsua_state PJSUA_STATE_RUNNING = new pjsua_state("PJSUA_STATE_RUNNING");
  public final static pjsua_state PJSUA_STATE_CLOSING = new pjsua_state("PJSUA_STATE_CLOSING");

  public final int swigValue() {
    return swigValue;
  }

  public String toString() {
    return swigName;
  }

  public static pjsua_state swigToEnum(int swigValue) {
    if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
      return swigValues[swigValue];
    for (int i = 0; i < swigValues.length; i++)
      if (swigValues[i].swigValue == swigValue)
        return swigValues[i];
    throw new IllegalArgumentException("No enum " + pjsua_state.class + " with value " + swigValue);
  }

  private pjsua_state(String swigName) {
    this.swigName = swigName;
    this.swigValue = swigNext++;
  }

  private pjsua_state(String swigName, int swigValue) {
    this.swigName = swigName;
    this.swigValue = swigValue;
    swigNext = swigValue+1;
  }

  private pjsua_state(String swigName, pjsua_state swigEnum) {
    this.swigName = swigName;
    this.swigValue = swigEnum.swigValue;
    swigNext = this.swigValue+1;
  }

  private static pjsua_state[] swigValues = { PJSUA_STATE_NULL, PJSUA_STATE_CREATED, PJSUA_STATE_INIT, PJSUA_STATE_STARTING, PJSUA_STATE_RUNNING, PJSUA_STATE_CLOSING };
  private static int swigNext = 0;
  private final int swigValue;
  private final String swigName;
}

