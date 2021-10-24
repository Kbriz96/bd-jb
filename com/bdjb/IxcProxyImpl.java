/*
 * Copyright (C) 2021 Andy Nguyen
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package com.bdjb;

import com.sony.gemstack.core.CoreAppContext;
import com.sony.gemstack.core.CoreIxcClassLoader;
import com.sony.gemstack.org.dvb.io.ixc.IxcProxy;
import java.rmi.RemoteException;

/** IxcProxy implementation that allows certain public methods to be invoked with privileges. */
class IxcProxyImpl extends IxcProxy {
  private static IxcProxyImpl instance;

  private Object remote;

  private IxcProxyImpl(CoreIxcClassLoader localClassLoader, CoreIxcClassLoader remoteClassLoader) {
    super(localClassLoader, remoteClassLoader);
  }

  static synchronized IxcProxyImpl getInstance() {
    if (instance == null) {
      CoreIxcClassLoader coreIxcClassLoader = CoreAppContext.getContext().getIxcClassLoader();
      instance = new IxcProxyImpl(coreIxcClassLoader, coreIxcClassLoader);
    }
    return instance;
  }

  public Object getRemote() {
    return remote;
  }

  public void forgetRemote() {}

  /** Override to avoid serializing the return object. */
  protected Object replaceObject(Object obj, CoreIxcClassLoader coreIxcClassLoader)
      throws RemoteException {
    return obj;
  }

  public Object invokeMethod(Object obj, String name, String signature, Object[] args)
      throws Exception {
    this.remote = obj;
    return super.invokeMethod(args, name, signature);
  }
}
