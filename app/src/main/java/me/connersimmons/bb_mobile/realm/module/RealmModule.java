package me.connersimmons.bb_mobile.realm.module;

import me.connersimmons.bb_mobile.models.Account;
import me.connersimmons.bb_mobile.models.Project;

/**
 * Created by connersimmons on 3/15/16.
 */
@io.realm.annotations.RealmModule(classes = {Project.class, Account.class})
public class RealmModule {

}
