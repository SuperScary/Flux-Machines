package net.superscary.fluxmachines.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.HandlerThread;

public class Channel {

	private static int packetId = 0;
	private static int id () {
		return packetId++;
	}

	public static void register (final RegisterPayloadHandlersEvent event) {
		var registrar = event.registrar("1").executesOn(HandlerThread.NETWORK);
		registrar.playBidirectional(CrucibleBlockHit.TYPE, CrucibleBlockHit.STREAM_CODEC,
				new DirectionalPayloadHandler<>(CrucibleBlockHit.ClientPayloadHandler::handleDataOnMain, CrucibleBlockHit.ServerPayloadHandler::handleDataOnMain));
	}

}
