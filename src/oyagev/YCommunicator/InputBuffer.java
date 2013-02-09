package oyagev.YCommunicator;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

public class InputBuffer {
	
	
	private Queue<Byte> input_buff;
	private boolean escape_next;
	
	private Queue<Packet> packets;
	
	public InputBuffer(){
		packets = new LinkedList<Packet>();
		
		reset();
	}
	

	
	public void write(byte data){
		
		if (data == 0x7c && !escape_next){
			createPacket();
			reset();
		}else if (data == 0 && !escape_next){
			escape_next = true;
		}else {
			input_buff.add(new Byte(data));
			escape_next = false;
		}
	}
	
	public boolean hasPackets(){
		return packets.size() > 0;
	}
	public int availablePackets(){
		return packets.size();
	}
	
	public Packet popPacket(){
		if (hasPackets()){
			return packets.poll();
		}
		return null;
	}
	
	private void createPacket(){
		int payload_size = input_buff.size() - 2;
		if (payload_size > 0){
			ByteBuffer tmp_payload = ByteBuffer.allocate(payload_size);
			for(int i=0;i<payload_size;i++){
				tmp_payload.put(input_buff.remove().byteValue());
			}
			short checksum =0;
			byte element;
			while (!input_buff.isEmpty()){
				element = input_buff.remove().byteValue();
				checksum = (short) (checksum << 8 + element);
			}
			
			Packet packet = Packet.fromByteBuffer(tmp_payload);
			packets.add(packet);
		}
	}
	
	private void reset(){
		input_buff = new LinkedList<Byte>();
		escape_next = false;
		
	}
}
