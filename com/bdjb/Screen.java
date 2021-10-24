/*
 * Copyright (C) 2021 Andy Nguyen
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package com.bdjb;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

class Screen extends Container {
  private static final Font FONT = new Font(null, Font.PLAIN, 36);

  private static final ArrayList messages = new ArrayList();

  private static final Screen instance = new Screen();

  static Screen getInstance() {
    return instance;
  }

  static void println(String msg) {
    messages.add(msg);
    instance.repaint();
  }

  public void paint(Graphics g) {
    g.setFont(FONT);
    g.setColor(Color.WHITE);

    int x = 100;
    int y = 100;
    int height = g.getFontMetrics().getHeight();
    for (int i = 0; i < messages.size(); i++) {
      String msg = (String) messages.get(i);
      g.drawString(msg, x, y);
      y += height;
    }
  }
}
