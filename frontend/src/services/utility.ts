export const toTitleCase = (str: string): string => {
  str = str.replace("_", "-");
  return str.charAt(0).toUpperCase() + str.substring(1).toLowerCase();
};
