/*
 * Copyright (C) 2021 Andy Nguyen
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package com.bdjb;

import com.oracle.security.Service;
import java.util.List;
import java.util.Map;
import java.security.Provider;

/** Service subclass implementing ServiceInterface in order to be accessible with IxcProxyImpl. */
class ServiceImpl extends Service implements ServiceInterface {
  ServiceImpl(Provider provider) {
    super(provider);
  }

  ServiceImpl(
      Provider provider,
      String type,
      String algorithm,
      String className,
      List aliases,
      Map attributes) {
    super(provider, type, algorithm, className, aliases, attributes);
  }
}
