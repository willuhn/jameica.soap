/**********************************************************************
 * $Source: /cvsroot/jameica/jameica.soap/src/de/willuhn/jameica/soap/servlets/MyCXFServlet.java,v $
 * $Revision: 1.2 $
 * $Date: 2008/07/10 09:19:11 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.soap.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.xml.ws.Endpoint;

import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import de.willuhn.jameica.messaging.Message;
import de.willuhn.jameica.messaging.MessageConsumer;
import de.willuhn.jameica.soap.messaging.DeployServiceMessage;
import de.willuhn.logging.Logger;
import de.willuhn.util.Queue;


/**
 * Ueberschrieben, um manuell Services deployen zu koennen, ohne sie
 * in cxf.xml manuell deklarieren zu muessen. Auf diese Weise koennen
 * auch andere Plugins Web-Services zur Verfuegung stellen, ohne
 * die Datei aendern zu muessen.
 * Siehe http://cwiki.apache.org/CXF20DOC/servlet-transport.html
 */
public class MyCXFServlet extends CXFNonSpringServlet implements MessageConsumer
{
  private static boolean loaded = false;
  private static Queue queue = new Queue(100);

  /**
   * @see org.apache.cxf.transport.servlet.CXFNonSpringServlet#loadBus(javax.servlet.ServletConfig)
   */
  public void loadBus(ServletConfig config) throws ServletException
  {
    super.loadBus(config);
    BusFactory.setDefaultBus(this.getBus()); // Wir machen den Servlet-Bus zum Default-Bus
    
    // Wir deployen die aufgelaufenen Services.
    while (queue.size() > 0)
    {
      DeployServiceMessage msg = (DeployServiceMessage) queue.pop();
      deploy(msg.getName(),msg.getServiceInstance());
    }
    loaded = true;
  }

  /**
   * @see de.willuhn.jameica.messaging.MessageConsumer#autoRegister()
   */
  public boolean autoRegister()
  {
    return true;
  }

  /**
   * @see de.willuhn.jameica.messaging.MessageConsumer#getExpectedMessageTypes()
   */
  public Class[] getExpectedMessageTypes()
  {
    return new Class[]{DeployServiceMessage.class};
  }

  /**
   * @see de.willuhn.jameica.messaging.MessageConsumer#handleMessage(de.willuhn.jameica.messaging.Message)
   */
  public void handleMessage(Message message) throws Exception
  {
    // Wenn wir noch nicht initialisiert sind, sammeln wir die Services erstmal nur
    if (!loaded)
    {
      queue.push(message);
      return;
    }
    
    // Service deployen
    DeployServiceMessage msg = (DeployServiceMessage) message;
    deploy(msg.getName(),msg.getServiceInstance());
  }
  
  /**
   * Deployed den Service.
   * @param name Name des Service.
   * @param service Instanz des JAX-WS Services.
   */
  private void deploy(String name, Object service)
  {
    if (name == null)
    {
      Logger.warn("skip service deployment, no service name given");
      return;
    }
    if (service == null)
    {
      Logger.warn("skip service deployment, no service instance given");
      return;
    }

    Logger.info("deploying webservice " + service.getClass().getName() + " at /" + name);
    Endpoint.publish("/" + name,service);
  }
}


/**********************************************************************
 * $Log: MyCXFServlet.java,v $
 * Revision 1.2  2008/07/10 09:19:11  willuhn
 * *** empty log message ***
 *
 * Revision 1.1  2008/07/09 23:30:53  willuhn
 * @R Nicht benoetigte Jars (gemaess WHICH_JARS) entfernt
 * @N Deployment vereinfacht
 *
 **********************************************************************/
