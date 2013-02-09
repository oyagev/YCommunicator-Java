import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import org.junit.Test;

import oyagev.YCommunicator.*;

public class InputBufferTest {
	
	@Test
	public void testWrite(){
		byte[] bytes;
		InputBuffer inp = new InputBuffer();
		assertFalse(inp.hasPackets());
		bytes = new byte[] {2,3,4,0,0,0,0,0x7c};
		for(int i=0;i<bytes.length;i++){
			assertFalse(inp.hasPackets());
			inp.write(bytes[i]);
			
		}
		assertTrue(inp.hasPackets());
		
		bytes = new byte[] {5,1,2,3,4,5,6,7,0x7c};
		for(int i=0;i<bytes.length;i++){
			inp.write(bytes[i]);
		}
		
		Packet p = inp.popPacket();
		assertEquals(3, p.getPayloadSize());
		assertEquals(0, p.getChecksum());
		ByteBuffer buff = p.getPayload();
		assertEquals(3, buff.limit());
		
		
		p = inp.popPacket();
		assertEquals(6, p.getPayloadSize());
		assertEquals(0, p.getChecksum());
		buff = p.getPayload();
		assertEquals(6, buff.limit());
		
	}
}
