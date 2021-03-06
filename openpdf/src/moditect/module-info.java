module com.github.librepdf.openpdf {
	requires static java.desktop;
	requires jakarta.annotation;

	requires static org.bouncycastle.pkix;
	//requires static org.bouncycastle.provider;
	requires static com.github.spotbugs.spotbugs;
	requires static imageio.tiff;
	requires static org.apache.fop;

	exports com.lowagie.text;
	exports com.lowagie.text.alignment;
	exports com.lowagie.text.error_messages;
	exports com.lowagie.text.exceptions;
	exports com.lowagie.text.factories;
	exports com.lowagie.text.html;
	exports com.lowagie.text.html.simpleparser;
	exports com.lowagie.text.pdf;
	exports com.lowagie.text.pdf.codec;
	exports com.lowagie.text.pdf.codec.wmf;
	exports com.lowagie.text.pdf.collection;
	exports com.lowagie.text.pdf.crypto;
	exports com.lowagie.text.pdf.draw;
	exports com.lowagie.text.pdf.events;
	exports com.lowagie.text.pdf.fonts;
	exports com.lowagie.text.pdf.fonts.cmaps;
	exports com.lowagie.text.pdf.hyphenation;
	exports com.lowagie.text.pdf.interfaces;
	exports com.lowagie.text.pdf.internal;
	exports com.lowagie.text.pdf.parser;
}

