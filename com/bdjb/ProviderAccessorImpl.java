/*
 * Copyright (C) 2021 Andy Nguyen
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package com.bdjb;

import com.oracle.ProviderAccessor;
import com.oracle.ProviderAdapter;
import com.oracle.security.Service;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.security.Provider;
import java.security.Security;

/** ProviderAccessor implementation that allows arbitrary services to be added. */
class ProviderAccessorImpl implements ProviderAccessor {
  private final HashMap providerServices = new HashMap();

  ProviderAccessorImpl(Provider[] providers) {
    this.copyProviderServices(providers);
  }

  private void copyProviderServices(Provider[] providers) {
    for (int i = 0; i < providers.length; i++) {
      providerServices.put(providers[i], new HashSet(ProviderAdapter.getServices(providers[i])));
    }
  }

  void setProviderAccessor() {
    ProviderAdapter.setProviderAccessor(this);
  }

  public Service getService(Provider provider, String type, String algorithm) {
    Set services = getServices(provider);
    if (services != null) {
      Iterator iterator = services.iterator();
      while (iterator.hasNext()) {
        Service service = (Service) iterator.next();
        if (service.getType().equals(type)
            && (service.getAlgorithm().equals(algorithm)
                || service.getAliases().contains(algorithm))) {
          return service;
        }
      }
    }
    return null;
  }

  public Set getServices(Provider provider) {
    return (Set) providerServices.get(provider);
  }

  public void putService(Provider provider, Object service) {
    Set services = getServices(provider);
    if (services != null) {
      services.add(service);
    }
  }
}
