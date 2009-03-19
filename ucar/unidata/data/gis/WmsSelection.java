/*
 * $Id: WmsSelection.java,v 1.12 2006/12/01 20:42:32 jeffmc Exp $
 *
 * Copyright  1997-2004 Unidata Program Center/University Corporation for
 * Atmospheric Research, P.O. Box 3000, Boulder, CO 80307,
 * support@unidata.ucar.edu.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */


package ucar.unidata.data.gis;


import org.w3c.dom.Element;

import ucar.unidata.data.GeoLocationInfo;

import ucar.unidata.util.Misc;

import ucar.unidata.xml.XmlResourceCollection;
import ucar.unidata.xml.XmlUtil;


import java.util.ArrayList;
import java.util.List;




/**
 * Class for controlling the display of color images.
 * @author Jeff McWhirter
 * @version $Revision: 1.12 $
 */
public class WmsSelection {


    /** xml tag name */
    public static final String TAG_BACKGROUNDWMS = "backgroundwms";

    /** xml tag name */
    public static final String TAG_WMS = "wms";

    /** xml tag name */
    public static final String ATTR_SERVER = "server";


    /** xml tag name */
    public static final String ATTR_LEGENDURL = "legendurl";

    /** xml attribute name */
    public static final String ATTR_LAYER = "layer";

    /** xml attribute name */
    public static final String ATTR_TITLE = "title";

    /** xml attribute name */
    public static final String ATTR_SRS = "srs";

    public static final String ATTR_CRS = "crs";

    /** xml attribute name */
    public static final String ATTR_FORMAT = "format";

    /** xml attribute name */
    public static final String ATTR_VERSION = "version";

    /** xml attribute name */
    public static final String ATTR_BBOX = "bbox";

    /** xml attribute name */
    public static final String ATTR_OPAQUE = "opaque";

    /** url of legend icon */
    private String legendIcon;


    /** Will hold list of times */
    private List timeList;

    /** Allow bbox subsetting */
    private boolean allowSubsets = true;

    /** Fixed height of image */
    private int fixedHeight = -1;

    /** Fixed width of image */
    private int fixedWidth = -1;


    /** The description */
    private String description;

    /** The title */
    private String title;

    /** The server */
    private String server;

    /** The layer name */
    private String layer;

    /** The srs (projection id) */
    private String srs;

    /** Max bounds */
    private GeoLocationInfo bounds;

    /** Format */
    private String format;

    /** Version */
    private String version;


    /**
     * Default constructor.
     */
    public WmsSelection() {}


    /**
     * Constructor.
     *
     * @param server  The server
     * @param layer  The layer
     * @param title  The title
     * @param srs  The srs
     * @param format  The format
     * @param version  The version
     * @param bounds  The bounds
     */
    public WmsSelection(String server, String layer, String title,
                        String srs, String format, String version,
                        GeoLocationInfo bounds) {
        this.server  = server;
        this.layer   = layer;
        this.title   = title;
        this.srs     = srs;
        this.bounds  = bounds;
        this.format  = format;
        this.version = version;
    }


    /**
     * Append the given layer to our layer name. Union the  bounds
     *
     * @param layerName layer name to append
     * @param thatBounds its bounds
     */
    public void appendLayer(String layerName, GeoLocationInfo thatBounds) {
        layer  = layer + "," + layerName;
        bounds = bounds.union(thatBounds);
    }

    /**
     * Create the wms request
     *
     * @param boundsToUse The bounds to use
     * @param imageWidth  The width
     * @param imageHeight  The height
     *
     * @return  The request url
     */
    public String assembleRequest(GeoLocationInfo boundsToUse,
                                  int imageWidth, int imageHeight) {
        String url = server;
        if (url.indexOf("?") < 0) {
            url = url + "?";
        } else {
            url = url + "&";
        }
        double diff = Math.abs(boundsToUse.getMinLon()
                               - boundsToUse.getMaxLon());
        //System.err.println ("diff:" + diff);
        String bbox = boundsToUse.getMinLon() + "," + boundsToUse.getMinLat()
                      + "," + boundsToUse.getMaxLon() + ","
                      + boundsToUse.getMaxLat();


        /*        bbox = boundsToUse.getMinLon() + "," + boundsToUse.getMinLat()
                      + "," + boundsToUse.getMaxLon() + ","
                      + (boundsToUse.getMinLat()+diff);*/


        return url + "version=" + version + "&request=GetMap" +
        //          "&Exceptions=se_xml" + 
        "&Styles=" + "" + "&format=" + format + "&SRS=" + srs +
            "&CRS=" + srs + "&Layers="
                   + layer + "&BBOX=" + bbox + "&width=" + imageWidth
                   + "&height=" + imageHeight+"&reaspect=false";
    }

    /**
     * Set the Server property.
     *
     * @param value The new value for Server
     */
    public void setServer(String value) {
        server = value;
    }

    /**
     * Get the Server property.
     *
     * @return The Server
     */
    public String getServer() {
        return server;
    }

    /**
     * Set the srs property.
     *
     * @param value The new value for srs
     */
    public void setSRS(String value) {
        srs = value;
    }

    /**
     * Get the srs property.
     *
     * @return The srs
     */
    public String getSRS() {
        return srs;
    }


    /**
     * Set the Layer property.
     *
     * @param value The new value for Layer
     */
    public void setLayer(String value) {
        layer = value;
    }

    /**
     * Get the Layer property.
     *
     * @return The Layer
     */
    public String getLayer() {
        return layer;
    }

    /**
     * Set the Bounds property.
     *
     * @param value The new value for Bounds
     */
    public void setBounds(GeoLocationInfo value) {
        bounds = value;
    }

    /**
     * Get the Bounds property.
     *
     * @return The Bounds
     */
    public GeoLocationInfo getBounds() {
        return bounds;
    }



    /**
     * Set the Format property.
     *
     * @param value The new value for Format
     */
    public void setFormat(String value) {
        format = value;
    }

    /**
     * Get the Format property.
     *
     * @return The Format
     */
    public String getFormat() {
        return format;
    }


    /**
     * Set the Title property.
     *
     * @param value The new value for Title
     */
    public void setTitle(String value) {
        title = value;
    }

    /**
     * Get the Title property.
     *
     * @return The Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the Version property.
     *
     * @param value The new value for Version
     */
    public void setVersion(String value) {
        version = value;
    }

    /**
     * Get the Version property.
     *
     * @return The Version
     */
    public String getVersion() {
        return version;
    }



    /**
     * to string
     *
     * @return the title
     */
    public String toString() {
        return title;
    }




    /**
     * Process the xms xml resources
     *
     * @param xrc The resources
     *
     * @return List of WmsSelection objects defined by the xml resources
     */
    public static List parseWmsResources(XmlResourceCollection xrc) {
        List infos = new ArrayList();
        for (int i = 0; i < xrc.size(); i++) {
            Element root = xrc.getRoot(i);
            if (root == null) {
                continue;
            }
            List children = XmlUtil.findChildren(root, TAG_WMS);
            for (int wmsIdx = 0; wmsIdx < children.size(); wmsIdx++) {
                Element wmsNode = (Element) children.get(wmsIdx);
                double[] bbox =
                    Misc.parseDoubles(XmlUtil.getAttribute(wmsNode,
                        ATTR_BBOX));

                GeoLocationInfo bounds = new GeoLocationInfo(bbox[0],
                                             bbox[1], bbox[2], bbox[3]);
                String layer = XmlUtil.getAttribute(wmsNode, ATTR_LAYER);
                WmsSelection wmsSelection =
                    new WmsSelection(
                        XmlUtil.getAttribute(wmsNode, ATTR_SERVER), layer,
                        XmlUtil.getAttribute(wmsNode, ATTR_TITLE, layer),
                        XmlUtil.getAttribute(wmsNode, ATTR_SRS),
                        XmlUtil.getAttribute(
                            wmsNode, ATTR_FORMAT,
                            "image/jpeg"), XmlUtil.getAttribute(
                                wmsNode, ATTR_VERSION, "1.1.1"), bounds);

                infos.add(wmsSelection);
                if (XmlUtil.hasAttribute(wmsNode, ATTR_LEGENDURL)) {
                    wmsSelection.setLegendIcon(XmlUtil.getAttribute(wmsNode,
                            ATTR_LEGENDURL));
                }

            }


        }

        return infos;
    }


    /**
     * Set the FixedWidth property.
     *
     * @param value The new value for FixedWidth
     */
    public void setFixedWidth(int value) {
        fixedWidth = value;
    }

    /**
     * Get the FixedWidth property.
     *
     * @return The FixedWidth
     */
    public int getFixedWidth() {
        return fixedWidth;
    }

    /**
     * Set the FixedHeight property.
     *
     * @param value The new value for FixedHeight
     */
    public void setFixedHeight(int value) {
        fixedHeight = value;
    }

    /**
     * Get the FixedHeight property.
     *
     * @return The FixedHeight
     */
    public int getFixedHeight() {
        return fixedHeight;
    }


    /**
     * Set the AllowSubsets property.
     *
     * @param value The new value for AllowSubsets
     */
    public void setAllowSubsets(boolean value) {
        allowSubsets = value;
    }

    /**
     * Get the AllowSubsets property.
     *
     * @return The AllowSubsets
     */
    public boolean getAllowSubsets() {
        return allowSubsets;
    }

    /**
     * Set the Description property.
     *
     * @param value The new value for Description
     */
    public void setDescription(String value) {
        description = value;
    }

    /**
     * Get the Description property.
     *
     * @return The Description
     */
    public String getDescription() {
        return description;
    }


    /**
     * Set the LegendIcon property.
     *
     * @param value The new value for LegendIcon
     */
    public void setLegendIcon(String value) {
        legendIcon = value;
    }

    /**
     * Get the LegendIcon property.
     *
     * @return The LegendIcon
     */
    public String getLegendIcon() {
        return legendIcon;
    }


    /**
     * Set the TimeList property.
     *
     * @param value The new value for TimeList
     */
    public void setTimeList(List value) {
        timeList = value;
    }

    /**
     * Get the TimeList property.
     *
     * @return The TimeList
     */
    public List getTimeList() {
        return timeList;
    }




    /**
     * Overwrite
     *
     * @return hashcode
     */
    public int hashCode() {
        return (new Object[] {
            this.server, this.layer, this.title, this.srs, this.bounds,
            this.format, this.version
        }).hashCode();
    }



    /**
     * Overwrite
     *
     * @param o the object
     *
     * @return is equals
     */
    public boolean equals(Object o) {
        if ( !(o instanceof WmsSelection)) {
            return false;
        }
        WmsSelection that = (WmsSelection) o;
        return Misc.equals(this.server, that.server)
               && Misc.equals(this.layer, that.layer)
               && Misc.equals(this.title, that.title)
               && Misc.equals(this.srs, that.srs)
               && Misc.equals(this.bounds, that.bounds)
               && Misc.equals(this.format, that.format)
               && Misc.equals(this.version, that.version);
    }

}

