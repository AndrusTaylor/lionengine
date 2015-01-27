/*
 * Copyright (C) 2013-2014 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.b3dgs.lionengine.example.game.network.chat;

import java.io.IOException;
import java.util.Collection;

import com.b3dgs.lionengine.Align;
import com.b3dgs.lionengine.core.Graphic;
import com.b3dgs.lionengine.core.Sequence;
import com.b3dgs.lionengine.network.NetworkedWorldClient;
import com.b3dgs.lionengine.network.NetworkedWorldModelClient;
import com.b3dgs.lionengine.network.message.NetworkMessage;
import com.b3dgs.lionengine.network.purview.Networkable;
import com.b3dgs.lionengine.network.purview.NetworkableModel;
import com.b3dgs.lionengine.stream.FileReading;

/**
 * World implementation using AbstractWorld.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
class WorldClient
        extends World<NetworkedWorldModelClient>
        implements NetworkedWorldClient, Networkable
{
    /** Networkable. */
    private final Networkable networkableModel;

    /**
     * Constructor.
     * 
     * @param sequence The sequence reference.
     */
    public WorldClient(Sequence sequence)
    {
        super(sequence.getConfig());
        networkableModel = new NetworkableModel();
        networkedWorld = new NetworkedWorldModelClient(new MessageDecoder());
        networkedWorld.addListener(this);
        networkedWorld.addListener(chat);
        sequence.addKeyListener(chat);
    }

    /*
     * World
     */

    @Override
    public void applyCommand(String command)
    {
        if (command.startsWith("/name"))
        {
            final String name = command.substring(6);
            setName(name);
        }
    }

    @Override
    public void update(double extrp)
    {
        // Nothing to do
    }

    @Override
    public void render(Graphic g)
    {
        super.render(g);
        text.draw(g, width, 12, Align.RIGHT, "Ping=" + getPing() + "ms");
    }

    @Override
    protected void loading(FileReading file) throws IOException
    {
        // Nothing to do
    }

    /*
     * NetworkedWorld
     */

    @Override
    public void connect(String ip, int port)
    {
        networkedWorld.connect(ip, port);
    }

    @Override
    public void setName(String name)
    {
        networkedWorld.setName(name);
    }

    @Override
    public String getName()
    {
        return networkedWorld.getName();
    }

    @Override
    public int getPing()
    {
        return networkedWorld.getPing();
    }

    @Override
    public byte getId()
    {
        return networkedWorld.getId();
    }

    /*
     * ClientConnectedListener
     */

    @Override
    public void notifyConnectionEstablished(Byte id, String name)
    {
        final Client client = new Client();
        client.setName(name);
        clients.put(id, client);
        client.setClientId(id);
        chat.setClientId(id);
        setClientId(id);
        addNetworkable(client);
        addNetworkable(this);
        addNetworkable(chat);
    }

    @Override
    public void notifyMessageOfTheDay(String messageOfTheDay)
    {
        // Nothing to do
    }

    @Override
    public void notifyConnectionTerminated(Byte id)
    {
        clients.remove(id);
        removeNetworkable(this);
        removeNetworkable(chat);
    }

    /*
     * Networkable
     */

    @Override
    public void applyMessage(NetworkMessage message)
    {
        // Nothing to do
    }

    @Override
    public void addNetworkMessage(NetworkMessage message)
    {
        networkableModel.addNetworkMessage(message);
    }

    @Override
    public Collection<NetworkMessage> getNetworkMessages()
    {
        return networkableModel.getNetworkMessages();
    }

    @Override
    public void clearNetworkMessages()
    {
        networkableModel.clearNetworkMessages();
    }

    @Override
    public void setClientId(Byte id)
    {
        networkableModel.setClientId(id);
    }

    @Override
    public Byte getClientId()
    {
        return networkableModel.getClientId();
    }
}
