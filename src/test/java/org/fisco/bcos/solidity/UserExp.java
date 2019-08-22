package org.fisco.bcos.solidity;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.request.BcosFilter;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

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
public class UserExp extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506113bc806100206000396000f300608060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680634543837a14610067578063dd114ec1146100ee578063efc81a8c14610161578063fcd7e3c11461018c575b600080fd5b34801561007357600080fd5b506100d8600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610210565b6040518082815260200191505060405180910390f35b3480156100fa57600080fd5b5061015f600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919050505061072c565b005b34801561016d57600080fd5b50610176610c9f565b6040518082815260200191505060405180910390f35b34801561019857600080fd5b506101f3600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610e37565b604051808381526020018281526020019250505060405180910390f35b60008060008060008060008094506000935061022b89610e37565b809550819650505060008514151561064d576102456112a1565b92508273ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156102ab57600080fd5b505af11580156102bf573d6000803e3d6000fd5b505050506040513d60208110156102d557600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663e942b5168a6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260088152602001807f757365726e616d65000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b838110156103a857808201518184015260208101905061038d565b50505050905090810190601f1680156103d55780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b1580156103f557600080fd5b505af1158015610409573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff16632ef8ba74896040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260088152602001807f757365725f65787000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b1580156104b557600080fd5b505af11580156104c9573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff166331afac368a846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b8381101561058857808201518184015260208101905061056d565b50505050905090810190601f1680156105b55780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b1580156105d557600080fd5b505af11580156105e9573d6000803e3d6000fd5b505050506040513d60208110156105ff57600080fd5b8101908080519060200190929190505050905060018114156106245760009550610648565b7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe95505b610671565b7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff95505b7f91c95f04198617c60eaf2180fbca88fc192db379657df0e412a9f7dd4ebbe95d868a8a6040518084815260200180602001838152602001828103825284818151815260200191508051906020019080838360005b838110156106e15780820151818401526020810190506106c6565b50505050905090810190601f16801561070e5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a185965050505050505092915050565b600080600080600093506000925061074386610e37565b80955081945050506107536112a1565b91508173ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156107b957600080fd5b505af11580156107cd573d6000803e3d6000fd5b505050506040513d60208110156107e357600080fd5b810190808051906020019092919050505090508073ffffffffffffffffffffffffffffffffffffffff1663e942b516876040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260088152602001807f757365726e616d65000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b838110156108b657808201518184015260208101905061089b565b50505050905090810190601f1680156108e35780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561090357600080fd5b505af1158015610917573d6000803e3d6000fd5b505050508073ffffffffffffffffffffffffffffffffffffffff16632ef8ba748686016040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260088152602001807f757365725f65787000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b1580156109c557600080fd5b505af11580156109d9573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663bf2b70a187838573ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610a5f57600080fd5b505af1158015610a73573d6000803e3d6000fd5b505050506040513d6020811015610a8957600080fd5b81019080805190602001909291905050506040518463ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825285818151815260200191508051906020019080838360005b83811015610b69578082015181840152602081019050610b4e565b50505050905090810190601f168015610b965780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b158015610bb757600080fd5b505af1158015610bcb573d6000803e3d6000fd5b505050506040513d6020811015610be157600080fd5b8101908080519060200190929190505050507f595c84555846d6ab9f2fc416f9b057270a4df14deb63b5a37e45df4edea0624286866040518080602001838152602001828103825284818151815260200191508051906020019080838360005b83811015610c5c578082015181840152602081019050610c41565b50505050905090810190601f168015610c895780820380516001836020036101000a031916815260200191505b50935050505060405180910390a1505050505050565b600080600061100191508173ffffffffffffffffffffffffffffffffffffffff166356004b6a6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001806020018481038452600a8152602001807f745f757365725f65787000000000000000000000000000000000000000000000815250602001848103835260088152602001807f757365726e616d65000000000000000000000000000000000000000000000000815250602001848103825260088152602001807f757365725f6578700000000000000000000000000000000000000000000000008152506020019350505050602060405180830381600087803b158015610dbb57600080fd5b505af1158015610dcf573d6000803e3d6000fd5b505050506040513d6020811015610de557600080fd5b810190808051906020019092919050505090507fb5636cd912a73dcdb5b570dbe331dfa3e6435c93e029e642def2c8e40dacf210816040518082815260200191505060405180910390a1809250505090565b600080600080600080610e486112a1565b93508373ffffffffffffffffffffffffffffffffffffffff1663e8434e39888673ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610ecb57600080fd5b505af1158015610edf573d6000803e3d6000fd5b505050506040513d6020811015610ef557600080fd5b81019080805190602001909291905050506040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015610fa3578082015181840152602081019050610f88565b50505050905090810190601f168015610fd05780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b158015610ff057600080fd5b505af1158015611004573d6000803e3d6000fd5b505050506040513d602081101561101a57600080fd5b81019080805190602001909291905050509250600091508273ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561109557600080fd5b505af11580156110a9573d6000803e3d6000fd5b505050506040513d60208110156110bf57600080fd5b810190808051906020019092919050505060001415611106577fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8281915095509550611298565b8273ffffffffffffffffffffffffffffffffffffffff1663846719e060006040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180828152602001915050602060405180830381600087803b15801561117657600080fd5b505af115801561118a573d6000803e3d6000fd5b505050506040513d60208110156111a057600080fd5b8101908080519060200190929190505050905060008173ffffffffffffffffffffffffffffffffffffffff1663fda69fae6040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260088152602001807f757365725f657870000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561125557600080fd5b505af1158015611269573d6000803e3d6000fd5b505050506040513d602081101561127f57600080fd5b8101908080519060200190929190505050819150955095505b50505050915091565b600080600061100191508173ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018281038252600a8152602001807f745f757365725f65787000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561134b57600080fd5b505af115801561135f573d6000803e3d6000fd5b505050506040513d602081101561137557600080fd5b810190808051906020019092919050505090508092505050905600a165627a7a7230582041ec605d669400ee7bf138ee59f9dbd2e9ac3df57685d25561f0f685926dc5c40029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"username\",\"type\":\"string\"},{\"name\":\"user_exp\",\"type\":\"uint256\"}],\"name\":\"set_user_exp\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"username\",\"type\":\"string\"},{\"name\":\"exp\",\"type\":\"uint256\"}],\"name\":\"update_exp\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"create\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"username\",\"type\":\"string\"}],\"name\":\"select\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"}],\"name\":\"CreateResult\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"ret_code\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"username\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"exp\",\"type\":\"uint256\"}],\"name\":\"RegisterEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"username\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"exp\",\"type\":\"uint256\"}],\"name\":\"UpdateExp\",\"type\":\"event\"}]";

    public static final String FUNC_SET_USER_EXP = "set_user_exp";

    public static final String FUNC_UPDATE_EXP = "update_exp";

    public static final String FUNC_CREATE = "create";

    public static final String FUNC_SELECT = "select";

    public static final Event CREATERESULT_EVENT = new Event("CreateResult", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
    ;

    public static final Event REGISTEREVENT_EVENT = new Event("RegisterEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event UPDATEEXP_EVENT = new Event("UpdateExp", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected UserExp(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected UserExp(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected UserExp(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected UserExp(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> set_user_exp(String username, BigInteger user_exp) {
        final Function function = new Function(
                FUNC_SET_USER_EXP, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(username), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(user_exp)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void set_user_exp(String username, BigInteger user_exp, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_SET_USER_EXP, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(username), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(user_exp)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String set_user_expSeq(String username, BigInteger user_exp) {
        final Function function = new Function(
                FUNC_SET_USER_EXP, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(username), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(user_exp)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> update_exp(String username, BigInteger exp) {
        final Function function = new Function(
                FUNC_UPDATE_EXP, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(username), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(exp)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void update_exp(String username, BigInteger exp, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_UPDATE_EXP, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(username), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(exp)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String update_expSeq(String username, BigInteger exp) {
        final Function function = new Function(
                FUNC_UPDATE_EXP, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(username), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(exp)), 
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

    public RemoteCall<Tuple2<BigInteger, BigInteger>> select(String username) {
        final Function function = new Function(FUNC_SELECT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(username)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple2<BigInteger, BigInteger>>(
                new Callable<Tuple2<BigInteger, BigInteger>>() {
                    @Override
                    public Tuple2<BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public List<CreateResultEventResponse> getCreateResultEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CREATERESULT_EVENT, transactionReceipt);
        ArrayList<CreateResultEventResponse> responses = new ArrayList<CreateResultEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
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
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CREATERESULT_EVENT, log);
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

    public List<RegisterEventEventResponse> getRegisterEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REGISTEREVENT_EVENT, transactionReceipt);
        ArrayList<RegisterEventEventResponse> responses = new ArrayList<RegisterEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RegisterEventEventResponse typedResponse = new RegisterEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ret_code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.username = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.exp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RegisterEventEventResponse> registerEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, RegisterEventEventResponse>() {
            @Override
            public RegisterEventEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REGISTEREVENT_EVENT, log);
                RegisterEventEventResponse typedResponse = new RegisterEventEventResponse();
                typedResponse.log = log;
                typedResponse.ret_code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.username = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.exp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RegisterEventEventResponse> registerEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REGISTEREVENT_EVENT));
        return registerEventEventFlowable(filter);
    }

    public List<UpdateExpEventResponse> getUpdateExpEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(UPDATEEXP_EVENT, transactionReceipt);
        ArrayList<UpdateExpEventResponse> responses = new ArrayList<UpdateExpEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UpdateExpEventResponse typedResponse = new UpdateExpEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.username = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.exp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UpdateExpEventResponse> updateExpEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, UpdateExpEventResponse>() {
            @Override
            public UpdateExpEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(UPDATEEXP_EVENT, log);
                UpdateExpEventResponse typedResponse = new UpdateExpEventResponse();
                typedResponse.log = log;
                typedResponse.username = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.exp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UpdateExpEventResponse> updateExpEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UPDATEEXP_EVENT));
        return updateExpEventFlowable(filter);
    }

    @Deprecated
    public static UserExp load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new UserExp(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static UserExp load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new UserExp(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static UserExp load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new UserExp(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static UserExp load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new UserExp(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<UserExp> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(UserExp.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<UserExp> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(UserExp.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<UserExp> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(UserExp.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<UserExp> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(UserExp.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class CreateResultEventResponse {
        public Log log;

        public BigInteger count;
    }

    public static class RegisterEventEventResponse {
        public Log log;

        public BigInteger ret_code;

        public String username;

        public BigInteger exp;
    }

    public static class UpdateExpEventResponse {
        public Log log;

        public String username;

        public BigInteger exp;
    }
}
