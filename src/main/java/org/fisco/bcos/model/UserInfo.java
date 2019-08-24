package org.fisco.bcos.model;

import io.reactivex.Flowable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.request.BcosFilter;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple4;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class UserInfo extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50611972806100206000396000f300608060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063523178a11461005c578063b9719a6214610282578063efc81a8c146103d1575b600080fd5b34801561006857600080fd5b506100c3600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506103fc565b6040518080602001806020018060200180602001858103855289818151815260200191508051906020019080838360005b8381101561010f5780820151818401526020810190506100f4565b50505050905090810190601f16801561013c5780820380516001836020036101000a031916815260200191505b50858103845288818151815260200191508051906020019080838360005b8381101561017557808201518184015260208101905061015a565b50505050905090810190601f1680156101a25780820380516001836020036101000a031916815260200191505b50858103835287818151815260200191508051906020019080838360005b838110156101db5780820151818401526020810190506101c0565b50505050905090810190601f1680156102085780820380516001836020036101000a031916815260200191505b50858103825286818151815260200191508051906020019080838360005b83811015610241578082015181840152602081019050610226565b50505050905090810190601f16801561026e5780820380516001836020036101000a031916815260200191505b509850505050505050505060405180910390f35b34801561028e57600080fd5b506103bb600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610c6d565b6040518082815260200191505060405180910390f35b3480156103dd57600080fd5b506103e66117ae565b6040518082815260200191505060405180910390f35b606080606080600080600080600060608060608061100198508873ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018281038252600c8152602001807f745f757365725f696e666f310000000000000000000000000000000000000000815250602001915050602060405180830381600087803b1580156104b557600080fd5b505af11580156104c9573d6000803e3d6000fd5b505050506040513d60208110156104df57600080fd5b810190808051906020019092919050505097508773ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561055657600080fd5b505af115801561056a573d6000803e3d6000fd5b505050506040513d602081101561058057600080fd5b810190808051906020019092919050505096508773ffffffffffffffffffffffffffffffffffffffff1663e8434e398f896040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b8381101561064e578082015181840152602081019050610633565b50505050905090810190601f16801561067b5780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b15801561069b57600080fd5b505af11580156106af573d6000803e3d6000fd5b505050506040513d60208110156106c557600080fd5b810190808051906020019092919050505095508573ffffffffffffffffffffffffffffffffffffffff1663846719e060006040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180828152602001915050602060405180830381600087803b15801561074857600080fd5b505af115801561075c573d6000803e3d6000fd5b505050506040513d602081101561077257600080fd5b810190808051906020019092919050505094508473ffffffffffffffffffffffffffffffffffffffff16639c981fcb6040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260088152602001807f757365726e616d65000000000000000000000000000000000000000000000000815250602001915050600060405180830381600087803b15801561082557600080fd5b505af1158015610839573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f82011682018060405250602081101561086357600080fd5b81019080805164010000000081111561087b57600080fd5b8281019050602081018481111561089157600080fd5b81518560018202830111640100000000821117156108ae57600080fd5b505092919050505093508473ffffffffffffffffffffffffffffffffffffffff16639c981fcb6040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001915050600060405180830381600087803b15801561095857600080fd5b505af115801561096c573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f82011682018060405250602081101561099657600080fd5b8101908080516401000000008111156109ae57600080fd5b828101905060208101848111156109c457600080fd5b81518560018202830111640100000000821117156109e157600080fd5b505092919050505092508473ffffffffffffffffffffffffffffffffffffffff16639c981fcb6040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260088152602001807f6964656e74697479000000000000000000000000000000000000000000000000815250602001915050600060405180830381600087803b158015610a8b57600080fd5b505af1158015610a9f573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f820116820180604052506020811015610ac957600080fd5b810190808051640100000000811115610ae157600080fd5b82810190506020810184811115610af757600080fd5b8151856001820283011164010000000082111715610b1457600080fd5b505092919050505091508473ffffffffffffffffffffffffffffffffffffffff16639c981fcb6040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f6167656e63790000000000000000000000000000000000000000000000000000815250602001915050600060405180830381600087803b158015610bbe57600080fd5b505af1158015610bd2573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f820116820180604052506020811015610bfc57600080fd5b810190808051640100000000811115610c1457600080fd5b82810190506020810184811115610c2a57600080fd5b8151856001820283011164010000000082111715610c4757600080fd5b50509291905050509050838383839c509c509c509c505050505050505050509193509193565b600080600080600080600061100195508573ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018281038252600c8152602001807f745f757365725f696e666f310000000000000000000000000000000000000000815250602001915050602060405180830381600087803b158015610d1d57600080fd5b505af1158015610d31573d6000803e3d6000fd5b505050506040513d6020811015610d4757600080fd5b810190808051906020019092919050505094508473ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610dbe57600080fd5b505af1158015610dd2573d6000803e3d6000fd5b505050506040513d6020811015610de857600080fd5b810190808051906020019092919050505093508473ffffffffffffffffffffffffffffffffffffffff1663e8434e398c866040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015610eb6578082015181840152602081019050610e9b565b50505050905090810190601f168015610ee35780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b158015610f0357600080fd5b505af1158015610f17573d6000803e3d6000fd5b505050506040513d6020811015610f2d57600080fd5b810190808051906020019092919050505092508473ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610fa457600080fd5b505af1158015610fb8573d6000803e3d6000fd5b505050506040513d6020811015610fce57600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663e942b5168c6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260088152602001807f757365726e616d65000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b838110156110a1578082015181840152602081019050611086565b50505050905090810190601f1680156110ce5780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b1580156110ee57600080fd5b505af1158015611102573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e942b5168b6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b838110156111c65780820151818401526020810190506111ab565b50505050905090810190601f1680156111f35780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561121357600080fd5b505af1158015611227573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e942b5168a6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260088152602001807f6964656e74697479000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b838110156112eb5780820151818401526020810190506112d0565b50505050905090810190601f1680156113185780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561133857600080fd5b505af115801561134c573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e942b516896040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260068152602001807f6167656e63790000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b838110156114105780820151818401526020810190506113f5565b50505050905090810190601f16801561143d5780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561145d57600080fd5b505af1158015611471573d6000803e3d6000fd5b505050508473ffffffffffffffffffffffffffffffffffffffff166331afac368c846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015611530578082015181840152602081019050611515565b50505050905090810190601f16801561155d5780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b15801561157d57600080fd5b505af1158015611591573d6000803e3d6000fd5b505050506040513d60208110156115a757600080fd5b810190808051906020019092919050505090507fbbdbfdda9122af35234c96849b016b4003d8f5631337c45caed2e47eb1d8e9b08b8b8b8b6040518080602001806020018060200180602001858103855289818151815260200191508051906020019080838360005b8381101561162b578082015181840152602081019050611610565b50505050905090810190601f1680156116585780820380516001836020036101000a031916815260200191505b50858103845288818151815260200191508051906020019080838360005b83811015611691578082015181840152602081019050611676565b50505050905090810190601f1680156116be5780820380516001836020036101000a031916815260200191505b50858103835287818151815260200191508051906020019080838360005b838110156116f75780820151818401526020810190506116dc565b50505050905090810190601f1680156117245780820380516001836020036101000a031916815260200191505b50858103825286818151815260200191508051906020019080838360005b8381101561175d578082015181840152602081019050611742565b50505050905090810190601f16801561178a5780820380516001836020036101000a031916815260200191505b509850505050505050505060405180910390a1809650505050505050949350505050565b600080600061100191508173ffffffffffffffffffffffffffffffffffffffff166356004b6a6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001806020018481038452600c8152602001807f745f757365725f696e666f310000000000000000000000000000000000000000815250602001848103835260088152602001807f757365726e616d65000000000000000000000000000000000000000000000000815250602001848103825260168152602001807f6e616d652c206964656e746974792c206167656e6379000000000000000000008152506020019350505050602060405180830381600087803b1580156118ca57600080fd5b505af11580156118de573d6000803e3d6000fd5b505050506040513d60208110156118f457600080fd5b810190808051906020019092919050505090507fb5636cd912a73dcdb5b570dbe331dfa3e6435c93e029e642def2c8e40dacf210816040518082815260200191505060405180910390a18092505050905600a165627a7a723058200198a99ed62ce0d8dd66da19fe44b21bf9b1d05201a6c006770eeee016c0499d0029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[{\"name\":\"username\",\"type\":\"string\"}],\"name\":\"user_get\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"username\",\"type\":\"string\"},{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"identity\",\"type\":\"string\"},{\"name\":\"agency\",\"type\":\"string\"}],\"name\":\"user_create\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"create\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"}],\"name\":\"CreateResult\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"}],\"name\":\"InsertResult\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"username\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"name\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"identity\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"agency\",\"type\":\"string\"}],\"name\":\"UserInfo\",\"type\":\"event\"}]";

    public static final String FUNC_USER_GET = "user_get";

    public static final String FUNC_USER_CREATE = "user_create";

    public static final String FUNC_CREATE = "create";

    public static final Event CREATERESULT_EVENT = new Event("CreateResult", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
    ;

    public static final Event INSERTRESULT_EVENT = new Event("InsertResult", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
    ;

    public static final Event USERINFO_EVENT = new Event("UserInfo", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected UserInfo(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected UserInfo(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected UserInfo(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected UserInfo(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Tuple4<String, String, String, String>> user_get(String username) {
        final Function function = new Function(FUNC_USER_GET, 
                Arrays.<Type>asList(new Utf8String(username)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple4<String, String, String, String>>(
                new Callable<Tuple4<String, String, String, String>>() {
                    @Override
                    public Tuple4<String, String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, String, String, String>(
                                (String) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (String) results.get(2).getValue(),
                                (String) results.get(3).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> user_create(String username, String name, String identity, String agency) {
        final Function function = new Function(
                FUNC_USER_CREATE,
                Arrays.<Type>asList(new Utf8String(username),
                new Utf8String(name),
                new Utf8String(identity),
                new Utf8String(agency)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void user_create(String username, String name, String identity, String agency, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_USER_CREATE,
                Arrays.<Type>asList(new Utf8String(username),
                new Utf8String(name),
                new Utf8String(identity),
                new Utf8String(agency)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String user_createSeq(String username, String name, String identity, String agency) {
        final Function function = new Function(
                FUNC_USER_CREATE,
                Arrays.<Type>asList(new Utf8String(username),
                new Utf8String(name),
                new Utf8String(identity),
                new Utf8String(agency)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> create() {
        final Function function = new Function(
                FUNC_CREATE,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void create(TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_CREATE,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String createSeq() {
        final Function function = new Function(
                FUNC_CREATE,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public List<CreateResultEventResponse> getCreateResultEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CREATERESULT_EVENT, transactionReceipt);
        ArrayList<CreateResultEventResponse> responses = new ArrayList<CreateResultEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            CreateResultEventResponse typedResponse = new CreateResultEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.count = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CreateResultEventResponse> createResultEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, CreateResultEventResponse>() {
            @Override
            public CreateResultEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CREATERESULT_EVENT, log);
                CreateResultEventResponse typedResponse = new CreateResultEventResponse();
                typedResponse.log = log;
                typedResponse.count = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CreateResultEventResponse> createResultEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CREATERESULT_EVENT));
        return createResultEventFlowable(filter);
    }

    public List<InsertResultEventResponse> getInsertResultEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(INSERTRESULT_EVENT, transactionReceipt);
        ArrayList<InsertResultEventResponse> responses = new ArrayList<InsertResultEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            InsertResultEventResponse typedResponse = new InsertResultEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.count = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<InsertResultEventResponse> insertResultEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, InsertResultEventResponse>() {
            @Override
            public InsertResultEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(INSERTRESULT_EVENT, log);
                InsertResultEventResponse typedResponse = new InsertResultEventResponse();
                typedResponse.log = log;
                typedResponse.count = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<InsertResultEventResponse> insertResultEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(INSERTRESULT_EVENT));
        return insertResultEventFlowable(filter);
    }

    public List<UserInfoEventResponse> getUserInfoEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(USERINFO_EVENT, transactionReceipt);
        ArrayList<UserInfoEventResponse> responses = new ArrayList<UserInfoEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            UserInfoEventResponse typedResponse = new UserInfoEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.username = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.identity = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.agency = (String) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UserInfoEventResponse> userInfoEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, UserInfoEventResponse>() {
            @Override
            public UserInfoEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(USERINFO_EVENT, log);
                UserInfoEventResponse typedResponse = new UserInfoEventResponse();
                typedResponse.log = log;
                typedResponse.username = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.name = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.identity = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.agency = (String) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UserInfoEventResponse> userInfoEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(USERINFO_EVENT));
        return userInfoEventFlowable(filter);
    }

    @Deprecated
    public static UserInfo load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new UserInfo(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static UserInfo load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new UserInfo(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static UserInfo load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new UserInfo(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static UserInfo load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new UserInfo(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<UserInfo> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(UserInfo.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<UserInfo> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(UserInfo.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<UserInfo> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(UserInfo.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<UserInfo> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(UserInfo.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class CreateResultEventResponse {
        public Log log;

        public BigInteger count;
    }

    public static class InsertResultEventResponse {
        public Log log;

        public BigInteger count;
    }

    public static class UserInfoEventResponse {
        public Log log;

        public String username;

        public String name;

        public String identity;

        public String agency;
    }
}
