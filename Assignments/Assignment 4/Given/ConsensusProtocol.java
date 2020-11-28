public abstract class ConsensusProtocol<T> implements Consensus<T>
{

	public ConsensusProtocol(int threadCount)	{

	}

	public void propose(T value)	{
		
	}

	abstract public void decide();
}
