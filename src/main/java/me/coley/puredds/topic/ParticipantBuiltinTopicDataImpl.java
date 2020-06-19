package me.coley.puredds.topic;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.UserData;
import org.omg.dds.topic.BuiltinTopicKey;
import org.omg.dds.topic.ParticipantBuiltinTopicData;

/**
 * ParticipantBuiltinTopicData impl.
 *
 * @author Matt Coley
 */
public class ParticipantBuiltinTopicDataImpl implements ParticipantBuiltinTopicData {
	private final ServiceEnvironment environment;
	private BuiltinTopicKey key;
	private UserData data;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param key
	 * 		Topic key.
	 * @param data
	 * 		Topic data.
	 */
	public ParticipantBuiltinTopicDataImpl(ServiceEnvironment environment, BuiltinTopicKey key, UserData data) {
		this.environment = environment;
		this.key = key;
		this.data = data;
	}

	@Override
	public void copyFrom(ParticipantBuiltinTopicData src) {
		this.key = src.getKey();
		this.data = src.getUserData();
	}

	@Override
	public ParticipantBuiltinTopicData clone() {
		return new ParticipantBuiltinTopicDataImpl(getEnvironment(), getKey(), getUserData());
	}

	@Override
	public BuiltinTopicKey getKey() {
		return key;
	}

	@Override
	public UserData getUserData() {
		return data;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}
