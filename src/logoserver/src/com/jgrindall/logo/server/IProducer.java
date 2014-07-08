
/**
 * IProducer.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 *  
 * **/

package com.jgrindall.logo.server;

public interface IProducer{

  void stopProcessing();
  String getMessage();
  void removeMessage();

}