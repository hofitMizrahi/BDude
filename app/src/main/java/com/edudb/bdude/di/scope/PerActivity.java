package com.edudb.bdude.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by artyomokun on 04/07/2017.
 * Scope annotation marks the lifecycle of the component
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {

}
