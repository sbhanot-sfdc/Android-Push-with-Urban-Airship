package com.salesforce.casemgmt;

import com.salesforce.androidsdk.ui.SalesforceR;


public class SalesforceRImpl implements SalesforceR {
	/* Login */
	public int stringAccountType() { return R.string.account_type; }
	public int layoutLogin() {return R.layout.login; }
	public int idLoginWebView() {return R.id.oauth_webview; }
	public int stringGenericError() {return R.string.generic_error; }
	public int stringGenericAuthenticationErrorTitle() {return R.string.generic_authentication_error_title; } 
	public int stringGenericAuthenticationErrorBody() {return R.string.generic_authentication_error; }
	/* Passcode */
	public int layoutPasscode() {return R.layout.passcode; }
	public int idPasscodeTitle() {return R.id.passcode_title; }
	public int idPasscodeError() {return R.id.passcode_error; }
	public int idPasscodeInstructions() {return R.id.passcode_instructions; }
	public int idPasscodeText() {return R.id.passcode_text; }
	public int stringPasscodeCreateTitle() {return R.string.passcode_create_title; }
	public int stringPasscodeEnterTitle() {return R.string.passcode_enter_title; }
	public int stringPasscodeConfirmTitle() {return R.string.passcode_confirm_title; }
	public int stringPasscodeEnterInstructions() {return R.string.passcode_enter_instructions; }
	public int stringPasscodeCreateInstructions() {return R.string.passcode_create_instructions; }
	public int stringPasscodeConfirmInstructions() {return R.string.passcode_confirm_instructions; }
	public int stringPasscodeMinLength() {return R.string.passcode_min_length; }
	public int stringPasscodeTryAgain() {return R.string.passcode_try_again; }
	public int stringPasscodeFinal() {return R.string.passcode_final; }
	public int stringPasscodesDontMatch() {return R.string.passcodes_dont_match; }
	/* Server picker */
	public int idPickerCustomLabel() {return R.id.picker_custom_label; }
	public int idPickerCustomUrl() {return R.id.picker_custom_url; }
	public int stringServerUrlDefaultCustomLabel() {return R.string.server_url_default_custom_label;}
	public int stringServerUrlDefaultCustomUrl() {return R.string.server_url_default_custom_url;}
	public int stringServerUrlAddTitle() {return R.string.server_url_add_title;}
	public int stringServerUrlEditTitle() {return R.string.server_url_edit_title;}
	public int layoutCustomServerUrl() {return R.layout.custom_server_url; }
	public int idApplyButton() {return R.id.apply_button;}
	public int idCancelButton() {return R.id.cancel_button;}
	public int stringInvalidServerUrl() {return R.string.invalid_server_url;}
	public int idServerListGroup() {return R.id.server_list_group; }
	public int layoutServerPicker() {return R.layout.server_picker; }
	public int stringAuthLoginProduction() {return R.string.auth_login_production;}
	public int stringAuthLoginSandbox() {return R.string.auth_login_sandbox;}
	public int menuClearCustomUrl() {return R.menu.clear_custom_url; }
	public int idMenuClearCustomUrl() {return R.id.menu_clear_custom_url;}
	public int drawableEditIcon() {return R.drawable.edit_icon; }
	public int idShowCustomUrlEdit() {return R.id.show_custom_url_edit;}
}
