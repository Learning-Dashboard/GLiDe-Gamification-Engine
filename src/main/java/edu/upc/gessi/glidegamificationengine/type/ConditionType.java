package edu.upc.gessi.glidegamificationengine.type;

public enum ConditionType {
    ValueGreaterThan            { @Override public Integer getNumberOfRequiredParameters() { return 1; }; },
    ValueLessThan               { @Override public Integer getNumberOfRequiredParameters() { return 1; }; },
    ValueEqualTo                { @Override public Integer getNumberOfRequiredParameters() { return 1; }; },
    ValueGreaterThanOrEqualTo   { @Override public Integer getNumberOfRequiredParameters() { return 1; }; },
    ValueLessThanOrEqualTo      { @Override public Integer getNumberOfRequiredParameters() { return 1; }; },
    ValueOutsideOfRange         { @Override public Integer getNumberOfRequiredParameters() { return 2; }; },
    ValueInsideOfRange          { @Override public Integer getNumberOfRequiredParameters() { return 2; }; },
    ValueMaximum                { @Override public Integer getNumberOfRequiredParameters() { return 1; }; },
    ValueMinimum                { @Override public Integer getNumberOfRequiredParameters() { return 1; }; };

    public abstract Integer getNumberOfRequiredParameters();
}
