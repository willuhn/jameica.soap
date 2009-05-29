/**********************************************************************
 * $Source: /cvsroot/jameica/jameica.soap/src/de/willuhn/jameica/soap/messaging/PublishServiceMessageConsumer.java,v $
 * $Revision: 1.5 $
 * $Date: 2009/05/29 15:24:55 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.soap.messaging;

import java.net.InetAddress;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import de.willuhn.jameica.messaging.LookupService;
import de.willuhn.jameica.messaging.Message;
import de.willuhn.jameica.messaging.MessageConsumer;
import de.willuhn.jameica.messaging.QueryMessage;
import de.willuhn.jameica.messaging.TextMessage;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.Queue;

/**
 * Deployed die Web-Services.
 */
public class PublishServiceMessageConsumer implements MessageConsumer
{
  private static boolean registered = false;

  // Hier drin sammeln wir die Services, bis der Jetty samt CXF komplett
  // gestartet wurde. Erst dann koennen wir die Services publizieren.
  private Queue queue = new Queue(100);
  private boolean started = false;
  
  /**
   * Erzeugt eine Instanz des Consumers.
   * @return Instanz.
   */
  public final synchronized static PublishServiceMessageConsumer create()
  {
    final PublishServiceMessageConsumer consumer = new PublishServiceMessageConsumer();
    if (!registered)
    {
      Application.getMessagingFactory().getMessagingQueue("jameica.webadmin.started").registerMessageConsumer(new MessageConsumer() {
        /**
         * @see de.willuhn.jameica.messaging.MessageConsumer#handleMessage(de.willuhn.jameica.messaging.Message)
         */
        public synchronized void handleMessage(Message message) throws Exception
        {
          if (consumer.started || consumer.queue.size() == 0)
            return;
          // Damit werden wir benachrichtigt, wenn das Jetty+CXF online sind.
          consumer.started = true;
          
          int count = 0;
          while (consumer.queue.size() > 0)
          {
            publish((QueryMessage) consumer.queue.pop());
            count++;
          }
          Logger.info("*** " + count + " webservices deployed - soap connector ready ***");
        }
      
        /**
         * @see de.willuhn.jameica.messaging.MessageConsumer#getExpectedMessageTypes()
         */
        public Class[] getExpectedMessageTypes()
        {
          return new Class[]{TextMessage.class};
        }
      
        /**
         * @see de.willuhn.jameica.messaging.MessageConsumer#autoRegister()
         */
        public boolean autoRegister()
        {
          return false;
        }
      
      });
      registered = true;
    }
    return consumer;
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
    if (!started)
      queue.push(message);
    else
      publish((QueryMessage) message);
  }
  
  /**
   * Publiziert einen Service.
   * @param name Name des Service.
   * @param service Instanz des Services.
   * @throws Exception
   */
  private static void publish(QueryMessage msg) throws Exception
  {
    String name    = msg.getName();
    Object service = msg.getData();
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
    Logger.info("deploying webservice " + service.getClass().getName() + " at " + name);
    Endpoint.publish(name,service);

    LookupService.register("soap:" + createName(service),createUrl(name));
    Application.getMessagingFactory().getMessagingQueue("jameica.soap.publish.done").sendMessage(msg);
  }
  
  /**
   * Ermittelt den Service-Namen aus der Instanz.
   * @param service Service-Instanz.
   * @return Name des Service.
   * @throws Exception
   */
  private static String createName(Object service) throws Exception
  {
    WebService s = service.getClass().getAnnotation(WebService.class);
    if (s == null)
      return service.getClass().getName(); // Annotation fehlt. Dann nehmen wir den Klassennamen der Implementierung

    return s.endpointInterface();
  }
  /**
   * Erzeugt die SOAP-URL fuer einen Service-Namen.
   * @param name Service-Name.
   * @return URL.
   * @throws Exception
   */
  private static String createUrl(String name) throws Exception
  {
    InetAddress host = de.willuhn.jameica.webadmin.Settings.getAddress();
    String address = host == null ? Application.getCallback().getHostname() : host.getHostAddress();
    StringBuffer sb = new StringBuffer();
    sb.append("http");
    if (de.willuhn.jameica.webadmin.Settings.getUseSSL()) sb.append("s");
    sb.append("://");
    sb.append(address);
    sb.append(":");
    sb.append(de.willuhn.jameica.webadmin.Settings.getPort());
    sb.append("/soap");
    sb.append(name);
    return sb.toString();
  }
}


/*********************************************************************
 * $Log: PublishServiceMessageConsumer.java,v $
 * Revision 1.5  2009/05/29 15:24:55  willuhn
 * @N SOAP-Webservices im Lookup-Service registrieren, damit sie genersich im Netz gefunden werden
 *
 * Revision 1.4  2008/08/08 11:24:26  willuhn
 * @N Console-Logging von Java-Logging ausschalten. Da wir es auf den Jameica-Logger umbiegen, wuerde es sonst doppelt auf der Console erscheinen
 *
 * Revision 1.3  2008/08/06 18:00:58  willuhn
 * @N Message nach erfolgreichem Deployment eines Webservice senden
 *
 * Revision 1.2  2008/07/11 16:50:12  willuhn
 * *** empty log message ***
 *
 * Revision 1.1  2008/07/11 15:38:52  willuhn
 * @N Service-Deployment
 *
 **********************************************************************/