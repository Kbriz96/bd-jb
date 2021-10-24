/*
 * Copyright (C) 2021 Andy Nguyen
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package com.bdjb;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

/** Service interface with methods of interest throwing RemoteException. */
interface ServiceInterface extends Remote {
  public Object newInstance(Object constructorParameter)
      throws RemoteException, NoSuchAlgorithmException;
}
