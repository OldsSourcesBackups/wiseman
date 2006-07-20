//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-hudson-3037-ea3 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.01.25 at 11:05:42 AM PST 
//


package org.xmlsoap.schemas.ws._2005._06.management;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.namespace.QName;
import org.xmlsoap.schemas.ws._2005._06.management.AuthType;
import org.xmlsoap.schemas.ws._2005._06.management.BookmarkType;
import org.xmlsoap.schemas.ws._2005._06.management.ConnectionRetryType;
import org.xmlsoap.schemas.ws._2005._06.management.DroppedEventsType;
import org.xmlsoap.schemas.ws._2005._06.management.EnumerationModeType;
import org.xmlsoap.schemas.ws._2005._06.management.EventBlockType;
import org.xmlsoap.schemas.ws._2005._06.management.EventType;
import org.xmlsoap.schemas.ws._2005._06.management.FragmentTransferType;
import org.xmlsoap.schemas.ws._2005._06.management.ItemType;
import org.xmlsoap.schemas.ws._2005._06.management.LocaleType;
import org.xmlsoap.schemas.ws._2005._06.management.MaxEnvelopeSizeType;
import org.xmlsoap.schemas.ws._2005._06.management.ObjectFactory;
import org.xmlsoap.schemas.ws._2005._06.management.OptionSetType;
import org.xmlsoap.schemas.ws._2005._06.management.OptionType;
import org.xmlsoap.schemas.ws._2005._06.management.RenameType;
import org.xmlsoap.schemas.ws._2005._06.management.ResourceURIType;
import org.xmlsoap.schemas.ws._2005._06.management.SelectorSetType;
import org.xmlsoap.schemas.ws._2005._06.management.SelectorType;
import org.xmlsoap.schemas.ws._2005._06.management.XmlFragmentType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.xmlsoap.schemas.ws._2005._06.management package. 
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

    private final static QName _FaultDetail_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "FaultDetail");
    private final static QName _SelectorSet_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "SelectorSet");
    private final static QName _XmlFragment_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "XmlFragment");
    private final static QName _ResourceURI_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "ResourceURI");
    private final static QName _MaxTime_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "MaxTime");
    private final static QName _EnumerationMode_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "EnumerationMode");
    private final static QName _AckRequested_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "AckRequested");
    private final static QName _DroppedEvents_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "DroppedEvents");
    private final static QName _OperationTimeout_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "OperationTimeout");
    private final static QName _Events_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "Events");
    private final static QName _Item_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "Item");
    private final static QName _Locale_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "Locale");
    private final static QName _URL_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "URL");
    private final static QName _Auth_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "Auth");
    private final static QName _Bookmark_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "Bookmark");
    private final static QName _MaxEnvelopeSize_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "MaxEnvelopeSize");
    private final static QName _ConnectionRetry_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "ConnectionRetry");
    private final static QName _RenamedTo_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "RenamedTo");
    private final static QName _Rename_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "Rename");
    private final static QName _MaxElements_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "MaxElements");
    private final static QName _OptionSet_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "OptionSet");
    private final static QName _FragmentTransfer_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "FragmentTransfer");
    private final static QName _SendBookmarks_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "SendBookmarks");
    private final static QName _Heartbeats_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/06/management", "Heartbeats");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.xmlsoap.schemas.ws._2005._06.management
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SelectorType }
     * 
     */
    public SelectorType createSelectorType() {
        return new SelectorType();
    }

    /**
     * Create an instance of {@link OptionType }
     * 
     */
    public OptionType createOptionType() {
        return new OptionType();
    }

    /**
     * Create an instance of {@link EventType }
     * 
     */
    public EventType createEventType() {
        return new EventType();
    }

    /**
     * Create an instance of {@link EventBlockType }
     * 
     */
    public EventBlockType createEventBlockType() {
        return new EventBlockType();
    }

    /**
     * Create an instance of {@link ItemType }
     * 
     */
    public ItemType createItemType() {
        return new ItemType();
    }

    /**
     * Create an instance of {@link OptionSetType }
     * 
     */
    public OptionSetType createOptionSetType() {
        return new OptionSetType();
    }

    /**
     * Create an instance of {@link BookmarkType }
     * 
     */
    public BookmarkType createBookmarkType() {
        return new BookmarkType();
    }

    /**
     * Create an instance of {@link ConnectionRetryType }
     * 
     */
    public ConnectionRetryType createConnectionRetryType() {
        return new ConnectionRetryType();
    }

    /**
     * Create an instance of {@link FragmentTransferType }
     * 
     */
    public FragmentTransferType createFragmentTransferType() {
        return new FragmentTransferType();
    }

    /**
     * Create an instance of {@link LocaleType }
     * 
     */
    public LocaleType createLocaleType() {
        return new LocaleType();
    }

    /**
     * Create an instance of {@link XmlFragmentType }
     * 
     */
    public XmlFragmentType createXmlFragmentType() {
        return new XmlFragmentType();
    }

    /**
     * Create an instance of {@link ResourceURIType }
     * 
     */
    public ResourceURIType createResourceURIType() {
        return new ResourceURIType();
    }

    /**
     * Create an instance of {@link MaxEnvelopeSizeType }
     * 
     */
    public MaxEnvelopeSizeType createMaxEnvelopeSizeType() {
        return new MaxEnvelopeSizeType();
    }

    /**
     * Create an instance of {@link SelectorSetType }
     * 
     */
    public SelectorSetType createSelectorSetType() {
        return new SelectorSetType();
    }

    /**
     * Create an instance of {@link AuthType }
     * 
     */
    public AuthType createAuthType() {
        return new AuthType();
    }

    /**
     * Create an instance of {@link RenameType }
     * 
     */
    public RenameType createRenameType() {
        return new RenameType();
    }

    /**
     * Create an instance of {@link DroppedEventsType }
     * 
     */
    public DroppedEventsType createDroppedEventsType() {
        return new DroppedEventsType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "FaultDetail")
    public JAXBElement<String> createFaultDetail(String value) {
        return new JAXBElement<String>(_FaultDetail_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectorSetType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "SelectorSet")
    public JAXBElement<SelectorSetType> createSelectorSet(SelectorSetType value) {
        return new JAXBElement<SelectorSetType>(_SelectorSet_QNAME, SelectorSetType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XmlFragmentType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "XmlFragment")
    public JAXBElement<XmlFragmentType> createXmlFragment(XmlFragmentType value) {
        return new JAXBElement<XmlFragmentType>(_XmlFragment_QNAME, XmlFragmentType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResourceURIType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "ResourceURI")
    public JAXBElement<ResourceURIType> createResourceURI(ResourceURIType value) {
        return new JAXBElement<ResourceURIType>(_ResourceURI_QNAME, ResourceURIType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "MaxTime")
    public JAXBElement<Duration> createMaxTime(Duration value) {
        return new JAXBElement<Duration>(_MaxTime_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnumerationModeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "EnumerationMode")
    public JAXBElement<EnumerationModeType> createEnumerationMode(EnumerationModeType value) {
        return new JAXBElement<EnumerationModeType>(_EnumerationMode_QNAME, EnumerationModeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "AckRequested")
    public JAXBElement<Object> createAckRequested(Object value) {
        return new JAXBElement<Object>(_AckRequested_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DroppedEventsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "DroppedEvents")
    public JAXBElement<DroppedEventsType> createDroppedEvents(DroppedEventsType value) {
        return new JAXBElement<DroppedEventsType>(_DroppedEvents_QNAME, DroppedEventsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "OperationTimeout")
    public JAXBElement<Duration> createOperationTimeout(Duration value) {
        return new JAXBElement<Duration>(_OperationTimeout_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventBlockType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "Events")
    public JAXBElement<EventBlockType> createEvents(EventBlockType value) {
        return new JAXBElement<EventBlockType>(_Events_QNAME, EventBlockType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ItemType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "Item")
    public JAXBElement<ItemType> createItem(ItemType value) {
        return new JAXBElement<ItemType>(_Item_QNAME, ItemType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LocaleType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "Locale")
    public JAXBElement<LocaleType> createLocale(LocaleType value) {
        return new JAXBElement<LocaleType>(_Locale_QNAME, LocaleType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "URL")
    public JAXBElement<String> createURL(String value) {
        return new JAXBElement<String>(_URL_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "Auth")
    public JAXBElement<AuthType> createAuth(AuthType value) {
        return new JAXBElement<AuthType>(_Auth_QNAME, AuthType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookmarkType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "Bookmark")
    public JAXBElement<BookmarkType> createBookmark(BookmarkType value) {
        return new JAXBElement<BookmarkType>(_Bookmark_QNAME, BookmarkType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MaxEnvelopeSizeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "MaxEnvelopeSize")
    public JAXBElement<MaxEnvelopeSizeType> createMaxEnvelopeSize(MaxEnvelopeSizeType value) {
        return new JAXBElement<MaxEnvelopeSizeType>(_MaxEnvelopeSize_QNAME, MaxEnvelopeSizeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectionRetryType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "ConnectionRetry")
    public JAXBElement<ConnectionRetryType> createConnectionRetry(ConnectionRetryType value) {
        return new JAXBElement<ConnectionRetryType>(_ConnectionRetry_QNAME, ConnectionRetryType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RenameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "RenamedTo")
    public JAXBElement<RenameType> createRenamedTo(RenameType value) {
        return new JAXBElement<RenameType>(_RenamedTo_QNAME, RenameType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RenameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "Rename")
    public JAXBElement<RenameType> createRename(RenameType value) {
        return new JAXBElement<RenameType>(_Rename_QNAME, RenameType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "MaxElements")
    public JAXBElement<Long> createMaxElements(Long value) {
        return new JAXBElement<Long>(_MaxElements_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OptionSetType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "OptionSet")
    public JAXBElement<OptionSetType> createOptionSet(OptionSetType value) {
        return new JAXBElement<OptionSetType>(_OptionSet_QNAME, OptionSetType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FragmentTransferType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "FragmentTransfer")
    public JAXBElement<FragmentTransferType> createFragmentTransfer(FragmentTransferType value) {
        return new JAXBElement<FragmentTransferType>(_FragmentTransfer_QNAME, FragmentTransferType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "SendBookmarks")
    public JAXBElement<Object> createSendBookmarks(Object value) {
        return new JAXBElement<Object>(_SendBookmarks_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/06/management", name = "Heartbeats")
    public JAXBElement<Duration> createHeartbeats(Duration value) {
        return new JAXBElement<Duration>(_Heartbeats_QNAME, Duration.class, null, value);
    }

}