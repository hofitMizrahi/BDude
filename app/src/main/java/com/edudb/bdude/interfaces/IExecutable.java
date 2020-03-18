package com.edudb.bdude.interfaces;

//interface to execute with generic param
public interface IExecutable<Parameter> {
    void execute(Parameter parameter);
}
