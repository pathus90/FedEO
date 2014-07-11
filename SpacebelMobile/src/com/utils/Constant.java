package com.utils;


public class Constant {
	
	public static final int EARTH_RADIUS = 6371;
	public static final int DISTANCE_BETWEEN_TWO_LAT = 111;	
	
	/* WRS error codes */
	public static final String INVALID_PARAM_VALUE = "InvalidParameterValue";
	public static final String NO_APPLICABLE_CODE = "NoApplicableCode";
	
	
	/* Mime types */
	public static final String ATOM_MIME_TYPE = "application/atom+xml";
	public static final String RSS_MIME_TYPE = "application/rss+xml";
	public static final String SRU_MIME_TYPE = "application/sru+xml";
	public static final String GML_MIME_TYPE = "application/gml+xml";
	public static final String ISO_MIME_TYPE = "application/vnd.iso.19139+xml";
	public static final String RDF_MIME_TYPE = "application/rdf+xml";
	public static final  String XML_MIME_TYPE = "application/xml";
	public static final String JSON_MIME_TYPE = "application/x-suggestions+json";
	public static final String ORGANIZATION_NAME = "organizationName";
	public static final String QUERY = "query";
	public static final String TITLE = "title";
	public static final String START_DATE="startDate";
	public static final String END_DATE="endDate";
	public static final String PARENT_IDENTIFIER = "parentIdentifier";
	
	/* urn prefixes */
	public static String EOP_PREFIX = "EOP:";
	public static String EOP_LOWER_CASE_PREFIX = "eop:";
	public static String COLLECTION_PREFIX = "urn:ogc:def:";
	
	public static String HTTP_ACCEPT_PARAM = "httpAccept";
	/* namespace and prefixes */
	public static String OWS_NS = "http://www.opengis.net/ows";	
	public static String ENV_NS = "http://schemas.xmlsoap.org/soap/envelope/";
	public static String ATOM_NS = "http://www.w3.org/2005/Atom";
	public static String GMD_NS = "http://www.isotc211.org/2005/gmd";
	public static String GMX_NS = "http://www.isotc211.org/2005/gmx";
	public static String OS_NS = "http://a9.com/-/spec/opensearch/1.1/";
	public static String DC_NS = "http://purl.org/dc/elements/1.1/";
	public static String WRS_NS = "http://www.opengis.net/cat/wrs/1.0";
	public static String XLINK_NS = "http://www.w3.org/1999/xlink";
	public static String CSW_NS = "http://www.opengis.net/cat/csw/2.0.2";
	public static String RIM_NS = "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0";
	public static String EOP_NS = "http://www.opengis.net/eop/2.0";
	public static String OPT_NS = "http://www.opengis.net/opt/2.0";
	public static String SAR_NS = "http://www.opengis.net/sar/2.0";
	public static String ATM_NS = "http://www.opengis.net/atm/2.0";
	public static String GCO_NS = "http://www.isotc211.org/2005/gco";
	
	public static String ISO_RECORD_SCHEMA = "iso";
	public static String SERVER_CHOICE_RECORD_SCHEMA = "server-choice";
	public static String DC_RECORD_SCHEMA = "dc";
	public static String OM_RECORD_SCHEMA = "om";
	
	/* HTTP Parameters */
	public static String CLOUD_COVER_PARAM ="cloudCover";
	public static String SNOW_COVER_PARAM ="snowCover";
	public static String AZIMUTH_ANGLE_PARAM ="illuminationAzimuthAngle";
	public static String ELEVATION_ANGLE_PARAM ="illuminationElevationAngle";
	public static String POLARISATION_MODE_PARAM ="polarisationMode";
	public static String POLARISATION_CHANNELS_PARAM ="polarisationChannels";
	public static String MIN_INCIDENCE_ANGLE_PARAM ="minimumIncidenceAngle";
	public static String MAX_INCIDENCE_ANGLE_PARAM ="maximumIncidenceAngle";
	
	public static String RECORD_SCHEMA_PARAM = "recordSchema";
	public static String GEONAME_PARAM = "name";
	public static String SUBJECT_PARAM = "name";
	public static String RADIUS_PARAM = "radius";
	public static String FRAME_PARAM = "frame";
	public static String ORBIT_NUMBER_PARAM = "orbitNumber";
	public static String BBOX_PARAM = "bbox";
	public static String LAT_PARAM = "lat";
	public static String LON_PARAM = "lon";
	public static String MAX_REC_PARAM = "maximumRecords";
	public static String EXCEPTION_REPORT_ELEMENT = "ExceptionReport";	
	public static int KM_TO_M = 1000;
	
	/*URL*/
	public static String ENTRY_URL="http://smaad.spacebel.be/opensearch/request/?";
}

