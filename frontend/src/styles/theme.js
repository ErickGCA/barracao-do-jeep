// Design System - Tema Principal
export const theme = {
  // Paleta de Cores Moderna
  colors: {
    // Primary - Azul moderno e vibrante
    primary: {
      50: '#e6f4ff',
      100: '#bae0ff',
      200: '#91caff',
      300: '#69b1ff',
      400: '#4096ff',
      500: '#1677ff',  // Main
      600: '#0958d9',
      700: '#003eb3',
      800: '#002c8c',
      900: '#001d66',
    },

    // Success - Verde energético
    success: {
      50: '#f6ffed',
      100: '#d9f7be',
      200: '#b7eb8f',
      300: '#95de64',
      400: '#73d13d',
      500: '#52c41a',  // Main
      600: '#389e0d',
      700: '#237804',
      800: '#135200',
      900: '#092b00',
    },

    // Warning - Laranja vibrante
    warning: {
      50: '#fff7e6',
      100: '#ffe7ba',
      200: '#ffd591',
      300: '#ffc069',
      400: '#ffa940',
      500: '#fa8c16',  // Main
      600: '#d46b08',
      700: '#ad4e00',
      800: '#873800',
      900: '#612500',
    },

    // Error - Vermelho moderno
    error: {
      50: '#fff1f0',
      100: '#ffccc7',
      200: '#ffa39e',
      300: '#ff7875',
      400: '#ff4d4f',
      500: '#f5222d',  // Main
      600: '#cf1322',
      700: '#a8071a',
      800: '#820014',
      900: '#5c0011',
    },

    // Neutral - Cinzas modernos
    neutral: {
      50: '#fafafa',
      100: '#f5f5f5',
      200: '#e8e8e8',
      300: '#d9d9d9',
      400: '#bfbfbf',
      500: '#8c8c8c',
      600: '#595959',
      700: '#434343',
      800: '#262626',
      900: '#1f1f1f',
    },

    // Background
    bg: {
      primary: '#ffffff',
      secondary: '#fafafa',
      tertiary: '#f5f5f5',
      dark: '#141414',
    },

    // Text
    text: {
      primary: 'rgba(0, 0, 0, 0.88)',
      secondary: 'rgba(0, 0, 0, 0.65)',
      tertiary: 'rgba(0, 0, 0, 0.45)',
      disabled: 'rgba(0, 0, 0, 0.25)',
      inverse: '#ffffff',
    },
  },

  // Tipografia
  typography: {
    fontFamily: {
      primary: "-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif",
      mono: "'SFMono-Regular', Consolas, 'Liberation Mono', Menlo, Courier, monospace",
    },
    fontSize: {
      xs: '12px',
      sm: '14px',
      base: '16px',
      lg: '18px',
      xl: '20px',
      '2xl': '24px',
      '3xl': '30px',
      '4xl': '38px',
      '5xl': '48px',
    },
    fontWeight: {
      normal: 400,
      medium: 500,
      semibold: 600,
      bold: 700,
    },
    lineHeight: {
      tight: 1.2,
      normal: 1.5,
      relaxed: 1.75,
    },
  },

  // Espaçamento
  spacing: {
    xs: '4px',
    sm: '8px',
    md: '16px',
    lg: '24px',
    xl: '32px',
    '2xl': '48px',
    '3xl': '64px',
    '4xl': '96px',
  },

  // Border Radius
  borderRadius: {
    none: '0',
    sm: '4px',
    md: '8px',
    lg: '12px',
    xl: '16px',
    '2xl': '24px',
    full: '9999px',
  },

  // Sombras
  shadows: {
    sm: '0 1px 2px 0 rgba(0, 0, 0, 0.05)',
    base: '0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06)',
    md: '0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06)',
    lg: '0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05)',
    xl: '0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04)',
    '2xl': '0 25px 50px -12px rgba(0, 0, 0, 0.25)',
    inner: 'inset 0 2px 4px 0 rgba(0, 0, 0, 0.06)',
  },

  // Breakpoints
  breakpoints: {
    xs: '480px',
    sm: '576px',
    md: '768px',
    lg: '992px',
    xl: '1200px',
    xxl: '1600px',
  },

  // Transições
  transitions: {
    fast: '150ms cubic-bezier(0.4, 0, 0.2, 1)',
    base: '200ms cubic-bezier(0.4, 0, 0.2, 1)',
    slow: '300ms cubic-bezier(0.4, 0, 0.2, 1)',
  },

  // Z-index
  zIndex: {
    dropdown: 1000,
    sticky: 1020,
    fixed: 1030,
    modalBackdrop: 1040,
    modal: 1050,
    popover: 1060,
    tooltip: 1070,
  },
};

// Configuração do Ant Design Theme
export const antdTheme = {
  token: {
    colorPrimary: theme.colors.primary[500],
    colorSuccess: theme.colors.success[500],
    colorWarning: theme.colors.warning[500],
    colorError: theme.colors.error[500],
    colorInfo: theme.colors.primary[500],

    colorBgBase: theme.colors.bg.primary,
    colorBgContainer: theme.colors.bg.primary,
    colorBgElevated: theme.colors.bg.primary,
    colorBgLayout: theme.colors.bg.secondary,

    colorTextBase: theme.colors.text.primary,
    colorTextSecondary: theme.colors.text.secondary,
    colorTextTertiary: theme.colors.text.tertiary,
    colorTextQuaternary: theme.colors.text.disabled,

    fontFamily: theme.typography.fontFamily.primary,
    fontSize: 14,
    fontSizeHeading1: 38,
    fontSizeHeading2: 30,
    fontSizeHeading3: 24,
    fontSizeHeading4: 20,
    fontSizeHeading5: 16,

    borderRadius: 8,
    borderRadiusLG: 12,
    borderRadiusSM: 4,

    lineHeight: 1.5,
    lineHeightHeading1: 1.2,
    lineHeightHeading2: 1.3,

    controlHeight: 36,
    controlHeightLG: 44,
    controlHeightSM: 28,

    boxShadow: theme.shadows.base,
    boxShadowSecondary: theme.shadows.sm,
  },

  components: {
    Button: {
      primaryShadow: '0 2px 0 rgba(22, 119, 255, 0.1)',
      dangerShadow: '0 2px 0 rgba(245, 34, 45, 0.1)',
      borderRadius: 8,
      controlHeight: 40,
      controlHeightLG: 48,
      fontWeight: 500,
    },

    Card: {
      borderRadiusLG: 12,
      boxShadowTertiary: theme.shadows.md,
      paddingLG: 24,
    },

    Table: {
      borderRadius: 8,
      headerBg: theme.colors.bg.tertiary,
      headerColor: theme.colors.text.primary,
      headerSplitColor: 'transparent',
      rowHoverBg: theme.colors.primary[50],
    },

    Input: {
      borderRadius: 8,
      controlHeight: 40,
      controlHeightLG: 48,
      paddingBlock: 8,
    },

    Select: {
      borderRadius: 8,
      controlHeight: 40,
      controlHeightLG: 48,
    },

    Modal: {
      borderRadiusLG: 16,
      headerBg: theme.colors.bg.primary,
    },

    Menu: {
      itemBorderRadius: 8,
      itemHeight: 44,
      itemMarginBlock: 4,
      itemMarginInline: 4,
    },
  },
};

export default theme;
