/**********************************************************************
 * $Source: /cvsroot/jameica/jameica.soap/src/de/willuhn/jameica/soap/messaging/PublishServiceMessageConsumer.java,v $
 * $Revision: 1.1 $
 * $Date: 2008/07/11 15:38:52 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.soap.messaging;

import javax.xml.ws.Endpoint;

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
  // Hier drin sammeln wir die Services, bis der Jetty samt CXF komplett
  // gestartet wurde. Erst dann koennen wir die Services publizieren.
  private Queue queue = new Queue(100);
  private boolean started = false;
  
  /**
   * ct
   */
  public PublishServiceMessageConsumer()
  {
    Application.getMessagingFactory().getMessagingQueue("jameica.webadmin.started").registerMessageConsumer(new MessageConsumer() {
      /**
       * @see de.willuhn.jameica.messaging.MessageConsumer#handleMessage(de.willuhn.jameica.messaging.Message)
       */
      public synchronized void handleMessage(Message message) throws Exception
      {
        if (started)
          return;
        // Damit werden wir benachrichtigt, wenn das Jetty+CXF online sind.
        started = true;
        
        while (queue.size() > 0)
        {
          publish((QueryMessage) queue.pop());
        }
        
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
    Logger.info("deploying webservice " + service.getClass().getName() + " at /" + name);
    Endpoint.publish("/" + name,service);
  }
}


/*********************************************************************
 * $Log: PublishServiceMessageConsumer.java,v $
 * Revision 1.1  2008/07/11 15:38:52  willuhn
 * @N Service-Deployment
 *
 **********************************************************************/