//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b11-EA 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.06.29 at 11:33:17 PDT 
//


package org.xmlsoap.schemas.ws._2005._02.management;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.Duration;
import javax.xml.namespace.QName;
import org.xmlsoap.schemas.ws._2005._02.management.AckRequested;
import org.xmlsoap.schemas.ws._2005._02.management.Auth;
import org.xmlsoap.schemas.ws._2005._02.management.BookmarkType;
import org.xmlsoap.schemas.ws._2005._02.management.ConnectionRetry;
import org.xmlsoap.schemas.ws._2005._02.management.EventBlockType;
import org.xmlsoap.schemas.ws._2005._02.management.EventType;
import org.xmlsoap.schemas.ws._2005._02.management.FragmentTransfer;
import org.xmlsoap.schemas.ws._2005._02.management.Locale;
import org.xmlsoap.schemas.ws._2005._02.management.MaxEnvelopeSize;
import org.xmlsoap.schemas.ws._2005._02.management.OperationTimeout;
import org.xmlsoap.schemas.ws._2005._02.management.OptionSetType;
import org.xmlsoap.schemas.ws._2005._02.management.OptionType;
import org.xmlsoap.schemas.ws._2005._02.management.RenameType;
import org.xmlsoap.schemas.ws._2005._02.management.Replay;
import org.xmlsoap.schemas.ws._2005._02.management.SelectorSetType;
import org.xmlsoap.schemas.ws._2005._02.management.SelectorType;
import org.xmlsoap.schemas.ws._2005._02.management.SystemType;
import org.xmlsoap.schemas.ws._2005._02.management.XmlFragmentType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.xmlsoap.schemas.ws._2005._02.management package. 
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

    private final static QName _OptionSet_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/management", "OptionSet");
    private final static QName _RenamedTo_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/management", "RenamedTo");
    private final static QName _Rename_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/management", "Rename");
    private final static QName _Events_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/management", "Events");
    private final static QName _MaxTime_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/management", "MaxTime");
    private final static QName _XmlFragment_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/management", "XmlFragment");
    private final static QName _MaxElements_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/management", "MaxElements");
    private final static QName _SendBookmarks_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/management", "SendBookmarks");
    private final static QName _Bookmark_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/management", "Bookmark");
    private final static QName _System_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/management", "System");
    private final static QName _SelectorSet_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/management", "SelectorSet");
    private final static QName _Heartbeats_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/management", "Heartbeats");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.xmlsoap.schemas.ws._2005._02.management
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link org.xmlsoap.schemas.ws._2005._02.management.XmlFragmentType}
     * 
     */
    public XmlFragmentType createXmlFragmentType() {
        return new XmlFragmentType();
    }

    /**
     * Create an instance of {@link org.xmlsoap.schemas.ws._2005._02.management.SystemType}
     * 
     */
    public SystemType createSystemType() {
        return new SystemType();
    }

    /**
     * Create an instance of {@link org.xmlsoap.schemas.ws._2005._02.management.AckRequested}
     * 
     */
    public AckRequested createAckRequested() {
        return new AckRequested();
    }

    /**
     * Create an instance of {@link org.xmlsoap.schemas.ws._2005._02.management.EventBlockType}
     * 
     */
    public EventBlockType createEventBlockType() {
        return new EventBlockType();
    }

    /**
     * Create an instance of {@link org.xmlsoap.schemas.ws._2005._02.management.RenameType}
     * 
     */
    public RenameType createRenameType() {
        return new RenameType();
    }

    /**
     * Create an instance of {@link org.xmlsoap.schemas.ws._2005._02.management.Locale}
     * 
     */
    public Locale createLocale() {
        return new Locale();
    }

    /**
     * Create an instance of {@link org.xmlsoap.schemas.ws._2005._02.management.Replay}
     * 
     */
    public Replay createReplay() {
        return new Replay();
    }

    /**
     * Create an instance of {@link org.xmlsoap.schemas.ws._2005._02.management.SelectorSetType}
     * 
     */
    public SelectorSetType createSelectorSetType() {
        return new SelectorSetType();
    }

    /**
     * Create an instance of {@link org.xmlsoap.schemas.ws._2005._02.management.OperationTimeout}
     * 
     */
    public OperationTimeout createOperationTimeout() {
        return new OperationTimeout();
    }

    /**
     * Create an instance of {@link org.xmlsoap.schemas.ws._2005._02.management.OptionSetType}
     * 
     */
    public OptionSetType createOptionSetType() {
        return new OptionSetType();
    }

    /**
     * Create an instance of {@link org.xmlsoap.schemas.ws._2005._02.management.ConnectionRetry}
     * 
     */
    public ConnectionRetry createConnectionRetry() {
        return new ConnectionRetry();
    }

    /**
     * Create an instance of {@link org.xmlsoap.schemas.ws._2005._02.management.EventType}
     * 
     */
    public EventType createEventType() {
        return new EventType();
    }

    /**
     * Create an instance of {@link org.xmlsoap.schemas.ws._2005._02.management.MaxEnvelopeSize}
     * 
     */
    public MaxEnvelopeSize createMaxEnvelopeSize() {
        return new MaxEnvelopeSize();
    }

    /**
     * Create an instance of {@link org.xmlsoap.schemas.ws._2005._02.management.FragmentTransfer}
     * 
     */
    public FragmentTransfer createFragmentTransfer() {
        return new FragmentTransfer();
    }

    /**
     * Create an instance of {@link org.xmlsoap.schemas.ws._2005._02.management.BookmarkType}
     * 
     */
    public BookmarkType createBookmarkType() {
        return new BookmarkType();
    }

    /**
     * Create an instance of {@link org.xmlsoap.schemas.ws._2005._02.management.SelectorType}
     * 
     */
    public SelectorType createSelectorType() {
        return new SelectorType();
    }

    /**
     * Create an instance of {@link org.xmlsoap.schemas.ws._2005._02.management.OptionType}
     * 
     */
    public OptionType createOptionType() {
        return new OptionType();
    }

    /**
     * Create an instance of {@link org.xmlsoap.schemas.ws._2005._02.management.Auth}
     * 
     */
    public Auth createAuth() {
        return new Auth();
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement<org.xmlsoap.schemas.ws._2005._02.management.OptionSetType>}
     * 
     */
    @XmlElementDecl(name = "OptionSet", namespace = "http://schemas.xmlsoap.org/ws/2005/02/management")
    public JAXBElement<OptionSetType> createOptionSet(OptionSetType value) {
        return new JAXBElement<OptionSetType>(_OptionSet_QNAME, ((Class) OptionSetType.class), null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement<org.xmlsoap.schemas.ws._2005._02.management.RenameType>}
     * 
     */
    @XmlElementDecl(name = "RenamedTo", namespace = "http://schemas.xmlsoap.org/ws/2005/02/management")
    public JAXBElement<RenameType> createRenamedTo(RenameType value) {
        return new JAXBElement<RenameType>(_RenamedTo_QNAME, ((Class) RenameType.class), null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement<org.xmlsoap.schemas.ws._2005._02.management.RenameType>}
     * 
     */
    @XmlElementDecl(name = "Rename", namespace = "http://schemas.xmlsoap.org/ws/2005/02/management")
    public JAXBElement<RenameType> createRename(RenameType value) {
        return new JAXBElement<RenameType>(_Rename_QNAME, ((Class) RenameType.class), null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement<org.xmlsoap.schemas.ws._2005._02.management.EventBlockType>}
     * 
     */
    @XmlElementDecl(name = "Events", namespace = "http://schemas.xmlsoap.org/ws/2005/02/management")
    public JAXBElement<EventBlockType> createEvents(EventBlockType value) {
        return new JAXBElement<EventBlockType>(_Events_QNAME, ((Class) EventBlockType.class), null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement<javax.xml.datatype.Duration>}
     * 
     */
    @XmlElementDecl(name = "MaxTime", namespace = "http://schemas.xmlsoap.org/ws/2005/02/management")
    public JAXBElement<Duration> createMaxTime(Duration value) {
        return new JAXBElement<Duration>(_MaxTime_QNAME, ((Class) Duration.class), null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement<org.xmlsoap.schemas.ws._2005._02.management.XmlFragmentType>}
     * 
     */
    @XmlElementDecl(name = "XmlFragment", namespace = "http://schemas.xmlsoap.org/ws/2005/02/management")
    public JAXBElement<XmlFragmentType> createXmlFragment(XmlFragmentType value) {
        return new JAXBElement<XmlFragmentType>(_XmlFragment_QNAME, ((Class) XmlFragmentType.class), null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement<java.lang.Long>}
     * 
     */
    @XmlElementDecl(name = "MaxElements", namespace = "http://schemas.xmlsoap.org/ws/2005/02/management")
    public JAXBElement<Long> createMaxElements(Long value) {
        return new JAXBElement<Long>(_MaxElements_QNAME, ((Class) Long.class), null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement<java.lang.String>}
     * 
     */
    @XmlElementDecl(name = "SendBookmarks", namespace = "http://schemas.xmlsoap.org/ws/2005/02/management")
    @XmlJavaTypeAdapter(value = CollapsedStringAdapter.class)
    public JAXBElement<String> createSendBookmarks(String value) {
        return new JAXBElement<String>(_SendBookmarks_QNAME, ((Class) String.class), null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement<org.xmlsoap.schemas.ws._2005._02.management.BookmarkType>}
     * 
     */
    @XmlElementDecl(name = "Bookmark", namespace = "http://schemas.xmlsoap.org/ws/2005/02/management")
    public JAXBElement<BookmarkType> createBookmark(BookmarkType value) {
        return new JAXBElement<BookmarkType>(_Bookmark_QNAME, ((Class) BookmarkType.class), null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement<org.xmlsoap.schemas.ws._2005._02.management.SystemType>}
     * 
     */
    @XmlElementDecl(name = "System", namespace = "http://schemas.xmlsoap.org/ws/2005/02/management")
    public JAXBElement<SystemType> createSystem(SystemType value) {
        return new JAXBElement<SystemType>(_System_QNAME, ((Class) SystemType.class), null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement<org.xmlsoap.schemas.ws._2005._02.management.SelectorSetType>}
     * 
     */
    @XmlElementDecl(name = "SelectorSet", namespace = "http://schemas.xmlsoap.org/ws/2005/02/management")
    public JAXBElement<SelectorSetType> createSelectorSet(SelectorSetType value) {
        return new JAXBElement<SelectorSetType>(_SelectorSet_QNAME, ((Class) SelectorSetType.class), null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement<javax.xml.datatype.Duration>}
     * 
     */
    @XmlElementDecl(name = "Heartbeats", namespace = "http://schemas.xmlsoap.org/ws/2005/02/management")
    public JAXBElement<Duration> createHeartbeats(Duration value) {
        return new JAXBElement<Duration>(_Heartbeats_QNAME, ((Class) Duration.class), null, value);
    }

}
