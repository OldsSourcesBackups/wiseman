//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-hudson-3037-ea3 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.04.21 at 02:48:53 PM PDT 
//


package org.dmtf.schemas.wbem.wsman._1.wsman;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.dmtf.schemas.wbem.wsman._1.wsman.AccessDenied;
import org.dmtf.schemas.wbem.wsman._1.wsman.AlreadyExists;
import org.dmtf.schemas.wbem.wsman._1.wsman.AnyListType;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableAny;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableDuration;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableEmpty;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableInt;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableLanguage;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableNonNegativeInteger;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributablePositiveInteger;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableURI;
import org.dmtf.schemas.wbem.wsman._1.wsman.AuthType;
import org.dmtf.schemas.wbem.wsman._1.wsman.Concurrency;
import org.dmtf.schemas.wbem.wsman._1.wsman.ConnectionRetryType;
import org.dmtf.schemas.wbem.wsman._1.wsman.DeliveryRefused;
import org.dmtf.schemas.wbem.wsman._1.wsman.DialectableMixedDataType;
import org.dmtf.schemas.wbem.wsman._1.wsman.DroppedEventsType;
import org.dmtf.schemas.wbem.wsman._1.wsman.EncodingLimit;
import org.dmtf.schemas.wbem.wsman._1.wsman.EnumerationModeType;
import org.dmtf.schemas.wbem.wsman._1.wsman.EventType;
import org.dmtf.schemas.wbem.wsman._1.wsman.EventsType;
import org.dmtf.schemas.wbem.wsman._1.wsman.FragmentDialectNotSupported;
import org.dmtf.schemas.wbem.wsman._1.wsman.InternalError;
import org.dmtf.schemas.wbem.wsman._1.wsman.InvalidBookmark;
import org.dmtf.schemas.wbem.wsman._1.wsman.InvalidOptions;
import org.dmtf.schemas.wbem.wsman._1.wsman.InvalidParameter;
import org.dmtf.schemas.wbem.wsman._1.wsman.InvalidSelectors;
import org.dmtf.schemas.wbem.wsman._1.wsman.Locale;
import org.dmtf.schemas.wbem.wsman._1.wsman.MaxEnvelopeSizeType;
import org.dmtf.schemas.wbem.wsman._1.wsman.MixedDataType;
import org.dmtf.schemas.wbem.wsman._1.wsman.NoAck;
import org.dmtf.schemas.wbem.wsman._1.wsman.ObjectFactory;
import org.dmtf.schemas.wbem.wsman._1.wsman.OptionSet;
import org.dmtf.schemas.wbem.wsman._1.wsman.OptionType;
import org.dmtf.schemas.wbem.wsman._1.wsman.QuotaLimit;
import org.dmtf.schemas.wbem.wsman._1.wsman.RequestedEPRType;
import org.dmtf.schemas.wbem.wsman._1.wsman.SchemaValidationError;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorSetType;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorType;
import org.dmtf.schemas.wbem.wsman._1.wsman.TimedOut;
import org.dmtf.schemas.wbem.wsman._1.wsman.UnsupportedFeature;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.dmtf.schemas.wbem.wsman._1.wsman package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _OptimizeEnumeration_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "OptimizeEnumeration");
    private final static QName _Option_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "Option");
    private final static QName _FragmentDialect_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "FragmentDialect");
    private final static QName _RequestTotalItemsCountEstimate_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "RequestTotalItemsCountEstimate");
    private final static QName _MaxEnvelopeSize_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "MaxEnvelopeSize");
    private final static QName _DroppedEvents_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "DroppedEvents");
    private final static QName _ContentEncoding_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "ContentEncoding");
    private final static QName _FaultDetail_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "FaultDetail");
    private final static QName _Filter_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "Filter");
    private final static QName _Items_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "Items");
    private final static QName _SelectorSet_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "SelectorSet");
    private final static QName _XmlFragment_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "XmlFragment");
    private final static QName _EPRInvalid_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "EPRInvalid");
    private final static QName _Bookmark_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "Bookmark");
    private final static QName _MaxTime_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "MaxTime");
    private final static QName _EPRUnknown_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "EPRUnknown");
    private final static QName _ConnectionRetry_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "ConnectionRetry");
    private final static QName _EnumerationMode_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "EnumerationMode");
    private final static QName _Auth_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "Auth");
    private final static QName _FragmentTransfer_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "FragmentTransfer");
    private final static QName _Event_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "Event");
    private final static QName _ResourceURI_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "ResourceURI");
    private final static QName _AckRequested_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "AckRequested");
    private final static QName _TotalItemsCountEstimate_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "TotalItemsCountEstimate");
    private final static QName _MaxElements_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "MaxElements");
    private final static QName _Events_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "Events");
    private final static QName _SendBookmarks_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "SendBookmarks");
    private final static QName _EndOfSequence_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "EndOfSequence");
    private final static QName _RequestedEPR_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "RequestedEPR");
    private final static QName _Heartbeats_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "Heartbeats");
    private final static QName _Selector_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "Selector");
    private final static QName _OperationTimeout_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "OperationTimeout");
    private final static QName _RequestEPR_QNAME = new QName("http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", "RequestEPR");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.dmtf.schemas.wbem.wsman._1.wsman
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AttributableNonNegativeInteger }
     * 
     */
    public AttributableNonNegativeInteger createAttributableNonNegativeInteger() {
        return new AttributableNonNegativeInteger();
    }

    /**
     * Create an instance of {@link AttributableLanguage }
     * 
     */
    public AttributableLanguage createAttributableLanguage() {
        return new AttributableLanguage();
    }

    /**
     * Create an instance of {@link AnyListType }
     * 
     */
    public AnyListType createAnyListType() {
        return new AnyListType();
    }

    /**
     * Create an instance of {@link AttributableInt }
     * 
     */
    public AttributableInt createAttributableInt() {
        return new AttributableInt();
    }

    /**
     * Create an instance of {@link OptionType }
     * 
     */
    public OptionType createOptionType() {
        return new OptionType();
    }

    /**
     * Create an instance of {@link AttributableURI }
     * 
     */
    public AttributableURI createAttributableURI() {
        return new AttributableURI();
    }

    /**
     * Create an instance of {@link MaxEnvelopeSizeType }
     * 
     */
    public MaxEnvelopeSizeType createMaxEnvelopeSizeType() {
        return new MaxEnvelopeSizeType();
    }

    /**
     * Create an instance of {@link Locale }
     * 
     */
    public Locale createLocale() {
        return new Locale();
    }

    /**
     * Create an instance of {@link DroppedEventsType }
     * 
     */
    public DroppedEventsType createDroppedEventsType() {
        return new DroppedEventsType();
    }

    /**
     * Create an instance of {@link AttributableEmpty }
     * 
     */
    public AttributableEmpty createAttributableEmpty() {
        return new AttributableEmpty();
    }

    /**
     * Create an instance of {@link EventType }
     * 
     */
    public EventType createEventType() {
        return new EventType();
    }

    /**
     * Create an instance of {@link DeliveryRefused }
     * 
     */
    public DeliveryRefused createDeliveryRefused() {
        return new DeliveryRefused();
    }

    /**
     * Create an instance of {@link EventsType }
     * 
     */
    public EventsType createEventsType() {
        return new EventsType();
    }

    /**
     * Create an instance of {@link AttributablePositiveInteger }
     * 
     */
    public AttributablePositiveInteger createAttributablePositiveInteger() {
        return new AttributablePositiveInteger();
    }

    /**
     * Create an instance of {@link InvalidSelectors }
     * 
     */
    public InvalidSelectors createInvalidSelectors() {
        return new InvalidSelectors();
    }

    /**
     * Create an instance of {@link InternalError }
     * 
     */
    public InternalError createInternalError() {
        return new InternalError();
    }

    /**
     * Create an instance of {@link NoAck }
     * 
     */
    public NoAck createNoAck() {
        return new NoAck();
    }

    /**
     * Create an instance of {@link AttributableAny }
     * 
     */
    public AttributableAny createAttributableAny() {
        return new AttributableAny();
    }

    /**
     * Create an instance of {@link InvalidOptions }
     * 
     */
    public InvalidOptions createInvalidOptions() {
        return new InvalidOptions();
    }

    /**
     * Create an instance of {@link InvalidParameter }
     * 
     */
    public InvalidParameter createInvalidParameter() {
        return new InvalidParameter();
    }

    /**
     * Create an instance of {@link SelectorType }
     * 
     */
    public SelectorType createSelectorType() {
        return new SelectorType();
    }

    /**
     * Create an instance of {@link RequestedEPRType }
     * 
     */
    public RequestedEPRType createRequestedEPRType() {
        return new RequestedEPRType();
    }

    /**
     * Create an instance of {@link ConnectionRetryType }
     * 
     */
    public ConnectionRetryType createConnectionRetryType() {
        return new ConnectionRetryType();
    }

    /**
     * Create an instance of {@link UnsupportedFeature }
     * 
     */
    public UnsupportedFeature createUnsupportedFeature() {
        return new UnsupportedFeature();
    }

    /**
     * Create an instance of {@link AccessDenied }
     * 
     */
    public AccessDenied createAccessDenied() {
        return new AccessDenied();
    }

    /**
     * Create an instance of {@link SelectorSetType }
     * 
     */
    public SelectorSetType createSelectorSetType() {
        return new SelectorSetType();
    }

    /**
     * Create an instance of {@link TimedOut }
     * 
     */
    public TimedOut createTimedOut() {
        return new TimedOut();
    }

    /**
     * Create an instance of {@link QuotaLimit }
     * 
     */
    public QuotaLimit createQuotaLimit() {
        return new QuotaLimit();
    }

    /**
     * Create an instance of {@link AttributableDuration }
     * 
     */
    public AttributableDuration createAttributableDuration() {
        return new AttributableDuration();
    }

    /**
     * Create an instance of {@link Concurrency }
     * 
     */
    public Concurrency createConcurrency() {
        return new Concurrency();
    }

    /**
     * Create an instance of {@link OptionSet }
     * 
     */
    public OptionSet createOptionSet() {
        return new OptionSet();
    }

    /**
     * Create an instance of {@link DialectableMixedDataType }
     * 
     */
    public DialectableMixedDataType createDialectableMixedDataType() {
        return new DialectableMixedDataType();
    }

    /**
     * Create an instance of {@link AuthType }
     * 
     */
    public AuthType createAuthType() {
        return new AuthType();
    }

    /**
     * Create an instance of {@link MixedDataType }
     * 
     */
    public MixedDataType createMixedDataType() {
        return new MixedDataType();
    }

    /**
     * Create an instance of {@link FragmentDialectNotSupported }
     * 
     */
    public FragmentDialectNotSupported createFragmentDialectNotSupported() {
        return new FragmentDialectNotSupported();
    }

    /**
     * Create an instance of {@link InvalidBookmark }
     * 
     */
    public InvalidBookmark createInvalidBookmark() {
        return new InvalidBookmark();
    }

    /**
     * Create an instance of {@link EncodingLimit }
     * 
     */
    public EncodingLimit createEncodingLimit() {
        return new EncodingLimit();
    }

    /**
     * Create an instance of {@link SchemaValidationError }
     * 
     */
    public SchemaValidationError createSchemaValidationError() {
        return new SchemaValidationError();
    }

    /**
     * Create an instance of {@link AlreadyExists }
     * 
     */
    public AlreadyExists createAlreadyExists() {
        return new AlreadyExists();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributableEmpty }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "OptimizeEnumeration")
    public JAXBElement<AttributableEmpty> createOptimizeEnumeration(AttributableEmpty value) {
        return new JAXBElement<AttributableEmpty>(_OptimizeEnumeration_QNAME, AttributableEmpty.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OptionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "Option")
    public JAXBElement<OptionType> createOption(OptionType value) {
        return new JAXBElement<OptionType>(_Option_QNAME, OptionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributableURI }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "FragmentDialect")
    public JAXBElement<AttributableURI> createFragmentDialect(AttributableURI value) {
        return new JAXBElement<AttributableURI>(_FragmentDialect_QNAME, AttributableURI.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributableEmpty }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "RequestTotalItemsCountEstimate")
    public JAXBElement<AttributableEmpty> createRequestTotalItemsCountEstimate(AttributableEmpty value) {
        return new JAXBElement<AttributableEmpty>(_RequestTotalItemsCountEstimate_QNAME, AttributableEmpty.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MaxEnvelopeSizeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "MaxEnvelopeSize")
    public JAXBElement<MaxEnvelopeSizeType> createMaxEnvelopeSize(MaxEnvelopeSizeType value) {
        return new JAXBElement<MaxEnvelopeSizeType>(_MaxEnvelopeSize_QNAME, MaxEnvelopeSizeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DroppedEventsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "DroppedEvents")
    public JAXBElement<DroppedEventsType> createDroppedEvents(DroppedEventsType value) {
        return new JAXBElement<DroppedEventsType>(_DroppedEvents_QNAME, DroppedEventsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributableLanguage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "ContentEncoding")
    public JAXBElement<AttributableLanguage> createContentEncoding(AttributableLanguage value) {
        return new JAXBElement<AttributableLanguage>(_ContentEncoding_QNAME, AttributableLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "FaultDetail")
    public JAXBElement<String> createFaultDetail(String value) {
        return new JAXBElement<String>(_FaultDetail_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DialectableMixedDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "Filter")
    public JAXBElement<DialectableMixedDataType> createFilter(DialectableMixedDataType value) {
        return new JAXBElement<DialectableMixedDataType>(_Filter_QNAME, DialectableMixedDataType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AnyListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "Items")
    public JAXBElement<AnyListType> createItems(AnyListType value) {
        return new JAXBElement<AnyListType>(_Items_QNAME, AnyListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectorSetType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "SelectorSet")
    public JAXBElement<SelectorSetType> createSelectorSet(SelectorSetType value) {
        return new JAXBElement<SelectorSetType>(_SelectorSet_QNAME, SelectorSetType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MixedDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "XmlFragment")
    public JAXBElement<MixedDataType> createXmlFragment(MixedDataType value) {
        return new JAXBElement<MixedDataType>(_XmlFragment_QNAME, MixedDataType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributableEmpty }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "EPRInvalid")
    public JAXBElement<AttributableEmpty> createEPRInvalid(AttributableEmpty value) {
        return new JAXBElement<AttributableEmpty>(_EPRInvalid_QNAME, AttributableEmpty.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributableAny }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "Bookmark")
    public JAXBElement<AttributableAny> createBookmark(AttributableAny value) {
        return new JAXBElement<AttributableAny>(_Bookmark_QNAME, AttributableAny.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributableDuration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "MaxTime")
    public JAXBElement<AttributableDuration> createMaxTime(AttributableDuration value) {
        return new JAXBElement<AttributableDuration>(_MaxTime_QNAME, AttributableDuration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributableEmpty }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "EPRUnknown")
    public JAXBElement<AttributableEmpty> createEPRUnknown(AttributableEmpty value) {
        return new JAXBElement<AttributableEmpty>(_EPRUnknown_QNAME, AttributableEmpty.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectionRetryType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "ConnectionRetry")
    public JAXBElement<ConnectionRetryType> createConnectionRetry(ConnectionRetryType value) {
        return new JAXBElement<ConnectionRetryType>(_ConnectionRetry_QNAME, ConnectionRetryType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnumerationModeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "EnumerationMode")
    public JAXBElement<EnumerationModeType> createEnumerationMode(EnumerationModeType value) {
        return new JAXBElement<EnumerationModeType>(_EnumerationMode_QNAME, EnumerationModeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "Auth")
    public JAXBElement<AuthType> createAuth(AuthType value) {
        return new JAXBElement<AuthType>(_Auth_QNAME, AuthType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DialectableMixedDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "FragmentTransfer")
    public JAXBElement<DialectableMixedDataType> createFragmentTransfer(DialectableMixedDataType value) {
        return new JAXBElement<DialectableMixedDataType>(_FragmentTransfer_QNAME, DialectableMixedDataType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "Event")
    public JAXBElement<EventType> createEvent(EventType value) {
        return new JAXBElement<EventType>(_Event_QNAME, EventType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributableURI }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "ResourceURI")
    public JAXBElement<AttributableURI> createResourceURI(AttributableURI value) {
        return new JAXBElement<AttributableURI>(_ResourceURI_QNAME, AttributableURI.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributableEmpty }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "AckRequested")
    public JAXBElement<AttributableEmpty> createAckRequested(AttributableEmpty value) {
        return new JAXBElement<AttributableEmpty>(_AckRequested_QNAME, AttributableEmpty.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributableNonNegativeInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "TotalItemsCountEstimate")
    public JAXBElement<AttributableNonNegativeInteger> createTotalItemsCountEstimate(AttributableNonNegativeInteger value) {
        return new JAXBElement<AttributableNonNegativeInteger>(_TotalItemsCountEstimate_QNAME, AttributableNonNegativeInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributablePositiveInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "MaxElements")
    public JAXBElement<AttributablePositiveInteger> createMaxElements(AttributablePositiveInteger value) {
        return new JAXBElement<AttributablePositiveInteger>(_MaxElements_QNAME, AttributablePositiveInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "Events")
    public JAXBElement<EventsType> createEvents(EventsType value) {
        return new JAXBElement<EventsType>(_Events_QNAME, EventsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributableEmpty }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "SendBookmarks")
    public JAXBElement<AttributableEmpty> createSendBookmarks(AttributableEmpty value) {
        return new JAXBElement<AttributableEmpty>(_SendBookmarks_QNAME, AttributableEmpty.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributableEmpty }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "EndOfSequence")
    public JAXBElement<AttributableEmpty> createEndOfSequence(AttributableEmpty value) {
        return new JAXBElement<AttributableEmpty>(_EndOfSequence_QNAME, AttributableEmpty.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RequestedEPRType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "RequestedEPR")
    public JAXBElement<RequestedEPRType> createRequestedEPR(RequestedEPRType value) {
        return new JAXBElement<RequestedEPRType>(_RequestedEPR_QNAME, RequestedEPRType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributableDuration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "Heartbeats")
    public JAXBElement<AttributableDuration> createHeartbeats(AttributableDuration value) {
        return new JAXBElement<AttributableDuration>(_Heartbeats_QNAME, AttributableDuration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectorType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "Selector")
    public JAXBElement<SelectorType> createSelector(SelectorType value) {
        return new JAXBElement<SelectorType>(_Selector_QNAME, SelectorType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributableDuration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "OperationTimeout")
    public JAXBElement<AttributableDuration> createOperationTimeout(AttributableDuration value) {
        return new JAXBElement<AttributableDuration>(_OperationTimeout_QNAME, AttributableDuration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributableEmpty }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd", name = "RequestEPR")
    public JAXBElement<AttributableEmpty> createRequestEPR(AttributableEmpty value) {
        return new JAXBElement<AttributableEmpty>(_RequestEPR_QNAME, AttributableEmpty.class, null, value);
    }

}
