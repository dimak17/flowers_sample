/**
 * Created by dima on 01.07.17.
 */
export const LATIN_VALIDATION = /^[a-zA-Z0-9 \\[\]+-.,!@?~`№#$%^&*=();\/|<>"\'_\\:áéñóúüÁÉÑÚÜ‌{}]+$/;
export const LATIN_VALIDATION_WITH_ENTER = /^[a-zA-Z0-9\n \\[\]+-.,!@?~`№#$%^&*=();\/|<>"\'_\\:áéñóúüÁÉÑÚÜ‌{}]+$/;
export const DIGITS = '^[0-9]+$';
export const EMAIL_VALIDATION = /[\\[\]+-,!@?~`№#$%^&*=();\/|<>"\'_\\:‌{} ]/;
export const EMAIL_FIRST_PART_VALIDATION = /^[a-zA-Z0-9 \\[\]+-.,!@?~`№#$%^&*=();\/|<>"\'_\\:‌{}]+$/;
export const PHONE_VALIDATION = /^[0-9 ()+]+$/;
