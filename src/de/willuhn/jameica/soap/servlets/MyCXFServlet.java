/**********************************************************************
 *
 * Copyright (c) by Olaf Willuhn
 * All rights reserved
 * GPLv2
 *
 **********************************************************************/

package de.willuhn.jameica.soap.servlets;

import javax.servlet.ServletConfig;

import org.apache.cxf.BusFactory;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import de.willuhn.jameica.messaging.Message;
import de.willuhn.jameica.messaging.MessageConsumer;
import de.willuhn.jameica.messaging.QueryMessage;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;


/**
 * Ueberschrieben, um manuell Services deployen zu koennen, ohne sie
 * in cxf.xml manuell deklarieren zu muessen. Auf diese Weise koennen
 * auch andere Plugins Web-Services zur Verfuegung stellen, ohne
 * die Datei aendern zu muessen.
 * Siehe http://cwiki.apache.org/CXF20DOC/servlet-transport.html
 */
public class MyCXFServlet extends CXFNonSpringServlet implements MessageConsumer
{
  /**
   * @see org.apache.cxf.transport.servlet.CXFNonSpringServlet#loadBus(javax.servlet.ServletConfig)
   */
  protected void loadBus(ServletConfig config)
  {
    super.loadBus(config);
    BusFactory.setDefaultBus(this.getBus()); // Wir machen den Servlet-Bus zum Default-Bus
    Application.getMessagingFactory().getMessagingQueue("jameica.soap.interceptor.add").registerMessageConsumer(this);
  }

  /**
   * @see de.willuhn.jameica.messaging.MessageConsumer#autoRegister()
   */
  public boolean autoRegister()
  {
    return false;
  }

  /**
   * @see de.willuhn.jameica.messaging.MessageConsumer#getExpectedMessageTypes()
   */
  public Class[] getExpectedMessageTypes()
  {
    return new Class[]{QueryMessage.class};
  }

  /**
   * @see de.willuhn.jameica.messaging.MessageConsumer#handleMessage(de.willuhn.jameica.messaging.Message)
   */
  public void handleMessage(Message message) throws Exception
  {
    QueryMessage msg = (QueryMessage) message;
    
    Object o = msg.getData();
    if (o == null || !(o instanceof Interceptor))
      return;
    
    String direction = msg.getName();
    if ("in".equals(direction))
    {
      Logger.info("register IN interceptor " + o.getClass().getName());
      this.getBus().getInInterceptors().add((Interceptor) o);
    }
    else
    {
      Logger.info("register OUT interceptor " + o.getClass().getName());
      this.getBus().getOutInterceptors().add((Interceptor) o);
    }
  }
}
