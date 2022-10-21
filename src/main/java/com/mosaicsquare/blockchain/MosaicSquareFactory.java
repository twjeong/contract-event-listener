package com.mosaicsquare.blockchain;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class MosaicSquareFactory extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b506109d3806100206000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c8063c4d66de81161005b578063c4d66de8146100e1578063e2f273bd146100f6578063e501d52914610109578063fc2cd26b1461011c57600080fd5b806327d8460c1461008257806377955245146100b257806381a6506f146100ca575b600080fd5b6100956100903660046108b9565b61012f565b6040516001600160a01b0390911681526020015b60405180910390f35b6000546100959061010090046001600160a01b031681565b6100d360025481565b6040519081526020016100a9565b6100f46100ef366004610878565b610375565b005b6100f4610104366004610878565b6104ad565b600154610095906001600160a01b031681565b6100f461012a366004610878565b6105cc565b60008054604051630935e01b60e21b81523360048201526101009091046001600160a01b0316906324d7806c9060240160206040518083038186803b15801561017757600080fd5b505afa15801561018b573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906101af9190610899565b6102155760405162461bcd60e51b815260206004820152602c60248201527f41646d696e3a204f6e6c79207468652061646d696e2063616e2063616c6c207460448201526b3434b990333ab731ba34b7b760a11b60648201526084015b60405180910390fd5b60006002541161028d5760405162461bcd60e51b815260206004820152603860248201527f436f6e74726163744d6f6e6579706970653a204d6f6e65797069706520636f6e60448201527f7472616374206973206e6f7420696e697469616c697a65640000000000000000606482015260840161020c565b6001546102a2906001600160a01b03166107a6565b6040517fb95ee7320000000000000000000000000000000000000000000000000000000081529091506001600160a01b0382169063b95ee732906102ec9086908690600401610933565b600060405180830381600087803b15801561030657600080fd5b505af115801561031a573d6000803e3d6000fd5b5050505083336001600160a01b0316826001600160a01b03167f860a502142808ee5c6bde93455c4e779227176662b57e93da0fd11a40eb004ba60025460405161036691815260200190565b60405180910390a49392505050565b60005460ff16156103ee5760405162461bcd60e51b815260206004820152602b60248201527f496e697469616c697a61626c653a20636f6e7472616374206973206e6f74206960448201527f6e697469616c697a696e67000000000000000000000000000000000000000000606482015260840161020c565b6001600160a01b0381163b61046b5760405162461bcd60e51b815260206004820152603060248201527f496e697469616c697a61626c653a20636f6e74726163742061646d696e206d7560448201527f7374206265206120636f6e747261637400000000000000000000000000000000606482015260840161020c565b600080546001600160a01b03909216610100027fffffffffffffffffffffff000000000000000000000000000000000000000000909216919091176001179055565b600054604051630935e01b60e21b81523360048201526101009091046001600160a01b0316906324d7806c9060240160206040518083038186803b1580156104f457600080fd5b505afa158015610508573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061052c9190610899565b61058d5760405162461bcd60e51b815260206004820152602c60248201527f41646d696e3a204f6e6c79207468652061646d696e2063616e2063616c6c207460448201526b3434b990333ab731ba34b7b760a11b606482015260840161020c565b600080546001600160a01b03909216610100027fffffffffffffffffffffff0000000000000000000000000000000000000000ff909216919091179055565b600054604051630935e01b60e21b81523360048201526101009091046001600160a01b0316906324d7806c9060240160206040518083038186803b15801561061357600080fd5b505afa158015610627573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061064b9190610899565b6106ac5760405162461bcd60e51b815260206004820152602c60248201527f41646d696e3a204f6e6c79207468652061646d696e2063616e2063616c6c207460448201526b3434b990333ab731ba34b7b760a11b606482015260840161020c565b6001600160a01b0381163b6107295760405162461bcd60e51b815260206004820152602260248201527f496d706c656d656e746174696f6e3a206d757374206265206120636f6e74726160448201527f6374000000000000000000000000000000000000000000000000000000000000606482015260840161020c565b600180547fffffffffffffffffffffffff0000000000000000000000000000000000000000166001600160a01b038316908117825560028054909201918290556040805191825260208201929092527f480ce8620d679b33e10136b1aeaed32b78ee90feb34bd38c220362b25869d72f910160405180910390a150565b60006040517f3d602d80600a3d3981f3363d3d373d3d3d363d7300000000000000000000000081528260601b60148201527f5af43d82803e903d91602b57fd5bf3000000000000000000000000000000000060288201526037816000f09150506001600160a01b03811661085c5760405162461bcd60e51b815260206004820152601660248201527f455243313136373a20637265617465206661696c656400000000000000000000604482015260640161020c565b919050565b80356001600160a01b038116811461085c57600080fd5b600060208284031215610889578081fd5b61089282610861565b9392505050565b6000602082840312156108aa578081fd5b81518015158114610892578182fd5b6000806000604084860312156108cd578182fd5b83359250602084013567ffffffffffffffff808211156108eb578384fd5b818601915086601f8301126108fe578384fd5b81358181111561090c578485fd5b8760208260061b8501011115610920578485fd5b6020830194508093505050509250925092565b6020808252818101839052600090604080840186845b87811015610990576001600160a01b0361096283610861565b1683528482013563ffffffff811680821461097b578788fd5b84870152509183019190830190600101610949565b509097965050505050505056fea2646970667358221220e0152fce5170ee39fe3d581abb4d768e1e9eb47c5fb02f33c7562962a765a87064736f6c63430008040033";

    public static final String FUNC_CONTRACTADMIN = "contractAdmin";

    public static final String FUNC_CONTRACTMONEYPIPE = "contractMoneypipe";

    public static final String FUNC_IMPLEMENTATION_MONEYPIPE = "implementation_Moneypipe";

    public static final String FUNC_INITIALIZE = "initialize";

    public static final String FUNC_UPDATEADMIN = "updateAdmin";

    public static final String FUNC_UPDATEIMPLEMENTATION_MONEYPIPE = "updateImplementation_Moneypipe";

    public static final String FUNC_VERSION_MONEYPIPE = "version_Moneypipe";

    public static final Event MONEYPIPECREATED_EVENT = new Event("MoneypipeCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event UPDATEDMONEYPIPE_EVENT = new Event("UpdatedMoneypipe", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected MosaicSquareFactory(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected MosaicSquareFactory(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected MosaicSquareFactory(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected MosaicSquareFactory(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<MoneypipeCreatedEventResponse> getMoneypipeCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(MONEYPIPECREATED_EVENT, transactionReceipt);
        ArrayList<MoneypipeCreatedEventResponse> responses = new ArrayList<MoneypipeCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MoneypipeCreatedEventResponse typedResponse = new MoneypipeCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.contractAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.creator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.version = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<MoneypipeCreatedEventResponse> moneypipeCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, MoneypipeCreatedEventResponse>() {
            @Override
            public MoneypipeCreatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(MONEYPIPECREATED_EVENT, log);
                MoneypipeCreatedEventResponse typedResponse = new MoneypipeCreatedEventResponse();
                typedResponse.log = log;
                typedResponse.contractAddress = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.creator = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.version = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<MoneypipeCreatedEventResponse> moneypipeCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MONEYPIPECREATED_EVENT));
        return moneypipeCreatedEventFlowable(filter);
    }

    public static List<UpdatedMoneypipeEventResponse> getUpdatedMoneypipeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(UPDATEDMONEYPIPE_EVENT, transactionReceipt);
        ArrayList<UpdatedMoneypipeEventResponse> responses = new ArrayList<UpdatedMoneypipeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UpdatedMoneypipeEventResponse typedResponse = new UpdatedMoneypipeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.implementation = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.version = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UpdatedMoneypipeEventResponse> updatedMoneypipeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, UpdatedMoneypipeEventResponse>() {
            @Override
            public UpdatedMoneypipeEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(UPDATEDMONEYPIPE_EVENT, log);
                UpdatedMoneypipeEventResponse typedResponse = new UpdatedMoneypipeEventResponse();
                typedResponse.log = log;
                typedResponse.implementation = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.version = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UpdatedMoneypipeEventResponse> updatedMoneypipeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UPDATEDMONEYPIPE_EVENT));
        return updatedMoneypipeEventFlowable(filter);
    }

    public RemoteFunctionCall<String> contractAdmin() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CONTRACTADMIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> contractMoneypipe(BigInteger id, List<tuple> members) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CONTRACTMONEYPIPE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id), 
                new org.web3j.abi.datatypes.DynamicArray<tuple>(tuple.class, members)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> implementation_Moneypipe() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_IMPLEMENTATION_MONEYPIPE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> initialize(String _contractAdmin) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INITIALIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractAdmin)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateAdmin(String newContractAdmin) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATEADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newContractAdmin)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateImplementation_Moneypipe(String implementation) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATEIMPLEMENTATION_MONEYPIPE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, implementation)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> version_Moneypipe() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VERSION_MONEYPIPE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static MosaicSquareFactory load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new MosaicSquareFactory(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static MosaicSquareFactory load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new MosaicSquareFactory(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static MosaicSquareFactory load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new MosaicSquareFactory(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static MosaicSquareFactory load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new MosaicSquareFactory(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<MosaicSquareFactory> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(MosaicSquareFactory.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<MosaicSquareFactory> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(MosaicSquareFactory.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<MosaicSquareFactory> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MosaicSquareFactory.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<MosaicSquareFactory> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MosaicSquareFactory.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class tuple extends StaticStruct {
        public String account;

        public BigInteger value;

        public tuple(String account, BigInteger value) {
            super(new org.web3j.abi.datatypes.Address(160, account), 
                    new org.web3j.abi.datatypes.generated.Uint32(value));
            this.account = account;
            this.value = value;
        }

        public tuple(Address account, Uint32 value) {
            super(account, value);
            this.account = account.getValue();
            this.value = value.getValue();
        }
    }

    public static class MoneypipeCreatedEventResponse extends BaseEventResponse {
        public String contractAddress;

        public String creator;

        public BigInteger id;

        public BigInteger version;
    }

    public static class UpdatedMoneypipeEventResponse extends BaseEventResponse {
        public String implementation;

        public BigInteger version;
    }
}
