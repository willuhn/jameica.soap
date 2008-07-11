/**********************************************************************
 * $Source: /cvsroot/jameica/jameica.soap/src/de/willuhn/jameica/soap/servlets/MyCXFServlet.java,v $
 * $Revision: 1.3 $
 * $Date: 2008/07/11 15:38:52 $
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

import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;


/**
 * Ueberschrieben, um manuell Services deployen zu koennen, ohne sie
 * in cxf.xml manuell deklarieren zu muessen. Auf diese Weise koennen
 * auch andere Plugins Web-Services zur Verfuegung stellen, ohne
 * die Datei aendern zu muessen.
 * Siehe http://cwiki.apache.org/CXF20DOC/servlet-transport.html
 */
public class MyCXFServlet extends CXFNonSpringServlet
{
  /**
   * @see org.apache.cxf.transport.servlet.CXFNonSpringServlet#loadBus(javax.servlet.ServletConfig)
   */
  public void loadBus(ServletConfig config) throws ServletException
  {
    super.loadBus(config);
    BusFactory.setDefaultBus(this.getBus()); // Wir machen den Servlet-Bus zum Default-Bus
  }
}


/**********************************************************************
 * $Log: MyCXFServlet.java,v $
 * Revision 1.3  2008/07/11 15:38:52  willuhn
 * @N Service-Deployment
 *
 * Revision 1.2  2008/07/10 09:19:11  willuhn
 * *** empty log message ***
 *
 * Revision 1.1  2008/07/09 23:30:53  willuhn
 * @R Nicht benoetigte Jars (gemaess WHICH_JARS) entfernt
 * @N Deployment vereinfacht
 *
 **********************************************************************/
