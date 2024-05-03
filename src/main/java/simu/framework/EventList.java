package simu.framework;

import java.util.PriorityQueue;

import java.util.Comparator;

public class EventList {
	private PriorityQueue<Event> list;

	public EventList() {
		// Asetetaan prioriteettijonon vertailuperusteeksi Event-olioiden aika.
		// Tämä vertailu varmistaa, että tapahtumat järjestetään niiden aikaleiman mukaan pienimmästä suurimpaan.
		this.list = new PriorityQueue<>(Comparator.comparing(Event::getTime));
	}

	// Poista ja palauta prioriteettijonon ensimmäinen tapahtuma, eli se tapahtuma, jonka aika on pienin.
	public Event remove() {
		return list.poll();  // Muutetaan remove() metodiksi poll(), joka palauttaa null, jos jono on tyhjä.
	}

	// Lisää uusi tapahtuma prioriteettijonoon.
	public void add(Event t) {
		list.add(t);
	}

	// Palauta seuraavan tapahtuman aika. Jos jono on tyhjä, palauta ääretön arvo.
	public double getNextTime() {
		Event next = list.peek();
		return (next != null) ? next.getTime() : Double.MAX_VALUE;
	}
}
